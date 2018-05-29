package com.suyang.dao.impl

import com.suyang.domain.Student
import org.junit.Assert
import org.junit.runners.MethodSorters
import org.junit.FixMethodOrder
import org.junit.Test
import java.util.*


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class StudentDaoImplTest {

    private val studentDao = StudentDaoImpl()

    @Test
    fun test1Insert() {
        var s: Student = Student(
                0,
                "aaaa",
                18,
                Date(),
                1
        )
        var result = studentDao.save(s)
        Assert.assertNotNull(result)
        Assert.assertTrue(result!!.id != 0)
    }

    @Test
    fun test2SelectAll() {
        val lists = studentDao.findAll()
        Assert.assertTrue(lists.size > 0)
    }

    @Test
    fun test3Update() {
        val lists = studentDao.findAll()
        val student = lists.get(0)
        student.name = "bbbb"
        Assert.assertNotNull(studentDao.update(student))
    }

    @Test
    fun test4Get() {
        val lists = studentDao.findAll()
        val student = lists.get(0)
        Assert.assertNotNull(studentDao.findOne(student.id))
    }

    @Test
    fun test5Delete() {
        val lists = studentDao.findAll()
        val student = lists.get(0)
        Assert.assertTrue(studentDao.delete(student.id) > 0)
    }
}