package com.example.studentapp

import ViewModel.FacultyViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.studentapp.dataClasses.Faculty
import com.example.studentapp.dataClasses.Student
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private var FacultyViewModel: FacultyViewModel = FacultyViewModel(mutableListOf())

    private lateinit var faculty_next_btn: Button
    private lateinit var faculty_prev_btn: Button
    private lateinit var student_next_btn: Button
    private lateinit var student_prev_btn: Button
    private lateinit var student_add_btn: Button
    private lateinit var student_edit_btn: Button
    private lateinit var student_drop_btn: Button
    private lateinit var faculty_textview: TextView
    private lateinit var student_textview: TextView

    private val INTENT_TAG_SERIZLIZABLE = "EXTRA_SERIALIZABLE_STIDENT_VIEWMODEL"
    private val INTENT_TAG_MAX_ID= "EXTRA_INT_MAX_ID"
    private val INTENT_TAG_FACULTY_ID = "EXTRA_INT_FACULTY_ID"

    private val addActivityLauncher: ActivityResultLauncher<Intent> =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        addActivityResultCallBack(it)
    }
    private val editActivityLauncher: ActivityResultLauncher<Intent> =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        editActivityResultCallBack(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InitViewObjects()
        val students = parseJSONStudent("students.json")
        for (i in students) {
            FacultyViewModel.addStudent(i.faculty_name,i)
        }

        for (i in FacultyViewModel.getFaculties()){
            Log.d("Faculty", i.name)
            for(j in i.sudents)
            {
                Log.d(
                    "hhhh",
                    "   " + j.first_name + " " + j.middle_name + " " + j.last_name + " " + j.faculty_name
                )
            }
        }
//        Log.d(
//            "hhhh",
//            "   " + FacultyViewModel.getFaculties()[0].sudents.size
//        )
        InitViewObjects()
        updateFacultyTextView()
        updateStudentTextView()
    }

    fun InitViewObjects(){
        faculty_next_btn = findViewById(R.id.next_faculty_btn);
        faculty_prev_btn = findViewById(R.id.prev_faculty_btn)
        student_next_btn = findViewById(R.id.next_student_btn)
        student_prev_btn = findViewById(R.id.prev_student_btn)
        student_add_btn = findViewById(R.id.add_student_btn)
        student_edit_btn = findViewById(R.id.edit_student_btn)
        student_drop_btn = findViewById(R.id.drop_student_btn)
        faculty_textview = findViewById(R.id.faculty_textview)
        student_textview = findViewById(R.id.student_textview)

        faculty_next_btn.setOnClickListener {
            FacultyViewModel.moveNext()
           // Log.d("Current", FacultyViewModel.getCurrentFaculty().toString())
            updateFacultyTextView()
            updateStudentTextView()
            Log.d("currentss", FacultyViewModel.currentFaculty.toString() + FacultyViewModel.getFaculties().size + " " + (FacultyViewModel.getCurrentFaculty()?.name))
        }
        faculty_prev_btn.setOnClickListener {
            FacultyViewModel.movePrev()
            //Log.d("Current", FacultyViewModel.getCurrentFaculty().toString())
            updateFacultyTextView()
            updateStudentTextView()
            Log.d("currentss", FacultyViewModel.currentFaculty.toString() + FacultyViewModel.getFaculties().size)
        }
        student_next_btn.setOnClickListener {
            FacultyViewModel.moveNextStudentPointer()
            updateFacultyTextView()
            updateStudentTextView()
        }
        student_prev_btn.setOnClickListener {
            FacultyViewModel.movePrevStudentPointer()
            updateFacultyTextView()
            updateStudentTextView()
        }
        student_add_btn.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            addActivityLauncher.launch(intent)
        }
        student_edit_btn.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("first_name", FacultyViewModel.getCurrentStudent()?.first_name)
            intent.putExtra("middle_name", FacultyViewModel.getCurrentStudent()?.middle_name)
            intent.putExtra("last_name", FacultyViewModel.getCurrentStudent()?.last_name)
            intent.putExtra("sex", FacultyViewModel.getCurrentStudent()?.sex)
            intent.putExtra("age", FacultyViewModel.getCurrentStudent()?.age)
            intent.putExtra("faculty", FacultyViewModel.getCurrentFaculty()?.name)
            editActivityLauncher.launch(intent)
        }
        student_drop_btn.setOnClickListener {
            FacultyViewModel.removeStudent(FacultyViewModel.getCurrentFaculty()?.name?:"", FacultyViewModel.getCurrentStudent())
            FacultyViewModel.moveNextStudentPointer()
            FacultyViewModel.checkStudents()
            updateFacultyTextView()
            updateStudentTextView()
        }
    }

    fun parseJSONFaculty(fileName: String): Array<Faculty>{
        val json_string = application.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        return Gson().fromJson(json_string, Array<Faculty>::class.java)
    }
    fun parseJSONStudent(fileName: String): Array<Student>{
        val json_string = application.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
        return Gson().fromJson(json_string, Array<Student>::class.java)
    }
    fun updateFacultyTextView(){
        val fac = FacultyViewModel.getCurrentFaculty()?.name
        Log.d("lololo", fac.toString())
       faculty_textview.text = fac
    }
    fun updateStudentTextView(){
        val current_student = FacultyViewModel.getCurrentStudent()

           if (current_student != null){
               student_textview.text = (
                       current_student.first_name
                               +" "
                               +current_student.middle_name
                               +" "
                               +current_student.last_name)
           }
        else{
            student_textview.text = " "
        }

    }
    fun addActivityResultCallBack(result: ActivityResult){
        if (result.resultCode == RESULT_OK){
            val first_name: String = result.data?.getStringExtra("first_name").toString()
            val middle_name: String = result.data?.getStringExtra("middle_name").toString()
            val last_name: String = result.data?.getStringExtra("last_name").toString()
            val age: Int = result.data?.getIntExtra("age",0) as Int
            val sex: String = result.data?.getStringExtra("sex").toString()
            val faculty: String = result.data?.getStringExtra("faculty").toString()

            Log.d("facname", faculty)

            FacultyViewModel.addStudent(
                faculty,
                Student(
                    (FacultyViewModel.getCurrentFaculty()?.sudents?.last()?.id ?: 0) +1 ,
                    first_name,
                    middle_name,
                    last_name,
                    age,
                    sex,
                    faculty
                )
            )
            updateFacultyTextView()
            updateStudentTextView()
        }
    }
    fun editActivityResultCallBack(result: ActivityResult){
        if (result.resultCode == RESULT_OK){
            val first_name: String = result.data?.getStringExtra("first_name").toString()
            val middle_name: String = result.data?.getStringExtra("middle_name").toString()
            val last_name: String = result.data?.getStringExtra("last_name").toString()
            val age: Int = result.data?.getIntExtra("age",0) as Int
            val sex: String = result.data?.getStringExtra("sex").toString()
            val faculty: String = result.data?.getStringExtra("faculty").toString()
            FacultyViewModel.addStudent(
                faculty,
                Student(
                    (FacultyViewModel.getCurrentFaculty()?.sudents?.last()?.id ?: 0) +1,
                    first_name,
                    middle_name,
                    last_name,
                    age,
                    sex,
                    faculty
                )
            )
            FacultyViewModel.removeStudent(FacultyViewModel.getCurrentFaculty()?.name ?: "", FacultyViewModel.getCurrentStudent())
            FacultyViewModel.checkStudents()
            updateFacultyTextView()
            updateStudentTextView()
        }
    }
}