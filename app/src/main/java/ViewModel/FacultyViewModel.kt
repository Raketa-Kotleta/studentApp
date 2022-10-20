package ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studentapp.dataClasses.Faculty
import com.example.studentapp.dataClasses.Student


class FacultyViewModel(faculties: MutableList<Faculty>): ViewModel()  {
    private var Faculties: MutableList<Faculty>
    var currentFaculty = 0
    var currentStudent = 0

    init {
        Faculties = faculties
    }
    public fun addStudent(faculty_name: String, student: Student){
        val faculty = getFacultyByName(faculty_name)?:addFaculty(createFaculty(faculty_name))
        faculty.sudents.add(student)
    }
    public fun removeStudent(faculty_name: String, student: Student?){
        val faculty = getFacultyByName(faculty_name)?:addFaculty(createFaculty(faculty_name))
        faculty.sudents.remove(student)
    }
    public fun checkStudents(){
        if (Faculties.get(currentFaculty).sudents.size == 0) {
            removeFaculty(Faculties.get(currentFaculty))
            moveNext()
        }
    }
    public fun addFaculty(faculty: Faculty): Faculty{
       Faculties.add(faculty)
        return faculty
    }
    public fun removeFaculty(faculty: Faculty){
        Faculties.remove(faculty)
    }


    private fun createFaculty(name: String):Faculty{
        return Faculty(
            name,
            mutableListOf()
        )
    }

    public fun moveNextStudentPointer(){
        currentStudent+=1;
        if(Faculties.size>0)
            if (currentStudent > Faculties[currentFaculty].sudents.size-1)
            currentStudent = 0
        else
            currentStudent = 0


    }
    public fun movePrevStudentPointer(){
        currentStudent-=1;
        if(Faculties.size>0)
            if (currentStudent < 0)
                currentStudent = Faculties[currentFaculty].sudents.size-1
        else
            currentStudent = 0
    }

    public fun moveNext(){
        currentFaculty++
        if (Faculties.size > 0){
            if (currentFaculty > Faculties.size-1)
                currentFaculty = 0
        }else{
            currentFaculty = 0
        }
        currentStudent = 0

    }
    public fun movePrev(){
        currentFaculty--
        if(Faculties.size > 0){
            if (currentFaculty < 0)
                currentFaculty = Faculties.size - 1
            else
                currentFaculty = 0

        }
        currentStudent = 0
    }
    public fun getCurrentFaculty():Faculty?{
        if (Faculties.size > 0)
           return Faculties[currentFaculty]
        return null
    }
    public fun getCurrentStudent():Student?{
        if (Faculties.size > 0)
        return Faculties[currentFaculty].sudents[currentStudent]
        return null
    }
    public fun getFaculties(): MutableList<Faculty>{
        return Faculties
    }
    public fun getFacultyByName(name: String): Faculty? {
        return Faculties.find{ it.name == name}?:null
    }
}