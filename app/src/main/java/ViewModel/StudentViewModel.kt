//package ViewModel
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import com.example.studentapp.dataClasses.Student
//
//class StudentViewModel(stdnts: List<Student>): ViewModel()  {
//    private lateinit var students: List<Student>
//    private lateinit var filtered_students: List<Student>
//    var current_student_num = 0
//    init{
//        students = stdnts
//    }
//    fun addNewStudent(student: Student){
//        students += student
//    }
//    fun removeStudent(id: Int){
//        students -= getStudentById(id)
//    }
//    fun editStudent(id: Int, EditedStudent: Student){
//        removeStudent(id)
//        addNewStudent(EditedStudent)
//    }
//    fun getStudentById(id: Int): Student {
//        return students.find {it.id == id}!!
//    }
//    fun filterStudentsByFaculty(faculty_id: Int): List<Student>{
//        filtered_students = students.filter { it.faculty_id == faculty_id}!!
//        return filtered_students
//    }
//    fun getCurrentStudent(): Student?{
//        return filtered_students[current_student_num]
//    }
//    fun moveNext(){
//        current_student_num++
//        if (current_student_num > filtered_students.lastIndex)
//            current_student_num = 0
//    }
//    fun movePrev(){
//        current_student_num--
//        if (current_student_num < 0)
//            current_student_num = filtered_students.lastIndex
//    }
//    fun getStudents():List<Student>{
//        return students
//    }
//    fun getFilteredStudents():List<Student>{
//        return filtered_students
//    }
//}