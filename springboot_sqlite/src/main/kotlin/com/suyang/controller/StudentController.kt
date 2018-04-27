package com.suyang.mina.controller

import com.suyang.dao.StudentDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.PathVariable
import com.suyang.domain.Student
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*


@RestController
class StudentController {
    @Autowired
    private lateinit var studentDao: StudentDao

    @RequestMapping(value = "/student/{id}", method = [(RequestMethod.GET)])
    fun findOne(@PathVariable("id") id: Int): Student? {
        var student = studentDao.findById(id)
        if (student.isPresent)
            return student.get()
        else
            return null
    }

    @RequestMapping(value = "/student", method = [(RequestMethod.GET)])
    fun findAll(): MutableList<Student>? {
        return studentDao.findAll()
    }

    @RequestMapping(value = "/student", method = [(RequestMethod.POST)])
    fun create(name: String,
               age: Int,
               birthday: Date): Student? {
        var student = Student(0, name, age, birthday)
        return studentDao.save(student)
    }

    @RequestMapping(value = "/student", method = [(RequestMethod.PUT)])
    fun modify(id: Int,
               name: String,
               age: Int,
               birthday: Date): Student? {
        var student = Student(id, name, age, birthday)
        return studentDao.save(student)
    }

    @RequestMapping(value = "/student/{id}", method = [(RequestMethod.DELETE)])
    fun delete(@PathVariable("id") id: Int) {
        studentDao.deleteById(id)
    }
}