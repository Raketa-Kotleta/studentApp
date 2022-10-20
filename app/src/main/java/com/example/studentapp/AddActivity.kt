package com.example.studentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class AddActivity : AppCompatActivity() {
    lateinit var addBtn: Button
    lateinit var first_name: EditText
    lateinit var middle_name: EditText
    lateinit var last_name: EditText
    lateinit var age: EditText
    lateinit var sex: EditText
    lateinit var faculty: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        InitViewObjects()
    }
    fun InitViewObjects(){
        addBtn = findViewById(R.id.add_button)
        addBtn.setOnClickListener {
            var isAllRight = true
            if (!onCheckLetters(first_name.text.toString())){
                first_name.setError("Должны содержаться только буквы")
                isAllRight = false
            }
            else{
                first_name.setError(null)
            }
            if (!onCheckLetters(middle_name.text.toString())){
                middle_name.setError("Должны содержаться только буквы")
                isAllRight = false
            }
            else{
                middle_name.setError(null)
            }
            if (!onCheckLetters(last_name.text.toString())){
                last_name.setError("Должны содержаться только буквы")
                isAllRight = false
            }
            else{
                last_name.setError(null)
            }
            if (!onCheckLetters(sex.text.toString())){
                sex.setError("Должны содержаться только буквы")
                isAllRight = false
            }
            else{
                sex.setError(null)
            }
            if (!onCheckLetters(faculty.text.toString())){
                faculty.setError("Должны содержаться только буквы")
                isAllRight = false
            }
            else{
                faculty.setError(null)
            }

           if (!onCheckNumber(age.text.toString())){
               age.setError("Должны содержаться только цифры")
               isAllRight = false
           }
           else{
            age.setError(null)
           }
            if (isAllRight)
                returnResponse()
        }
        first_name = findViewById(R.id.firstname_editview)
        middle_name = findViewById(R.id.middlename_editview)
        last_name = findViewById(R.id.lastname_editview)
        age = findViewById(R.id.age_editview)
        sex = findViewById(R.id.sex_editview)
        faculty = findViewById(R.id.faculty_editview1)
    }

    fun onCheckNumber(input: String): Boolean{
        return Regex("[0-9]+").matches(input)
    }
    fun onCheckLetters(input:String): Boolean{
        return  Regex("[a-zа-я]+", RegexOption.IGNORE_CASE).matches(input)
    }
    fun returnResponse(){
        val intent: Intent = Intent()
        intent.putExtra("first_name", first_name.text.toString())
        intent.putExtra("middle_name", middle_name.text.toString())
        intent.putExtra("last_name", last_name.text.toString())
        intent.putExtra("age",Integer.parseInt(age.text.toString()))
        intent.putExtra("sex", sex.text.toString())
        intent.putExtra("faculty", faculty.text.toString())
        setResult(RESULT_OK,intent)
        finish()
    }
}