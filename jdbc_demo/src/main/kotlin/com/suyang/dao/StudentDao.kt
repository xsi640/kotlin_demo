package com.suyang.dao

import com.suyang.domain.Student

interface StudentDao {
    fun findOne(id: Int): Student?
    fun findAll(): List<Student>
    fun count(): Int
    fun save(student: Student): Student?
    fun saveMany(students: List<Student>): Int
    fun delete(studentId: Int): Int
    fun update(student: Student): Student?
}