package com.suyang.dao.impl

import com.suyang.DBFactory
import com.suyang.dao.StudentDao
import com.suyang.domain.Student
import java.sql.*
import java.sql.SQLException
import java.sql.PreparedStatement


class StudentDaoImpl : StudentDao {
    val GET = "SELECT * FROM student WHERE id=?"
    val GET_ALL = "SELECT * FROM student"
    val COUNT = "SELECT count(-1) FROM student"
    val SAVE = "INSERT INTO student(name, age, birthday, store_id) VALUES(?,?,?,?)"
    val DELETE = "DELETE FROM student WHERE id=?"
    val UPDATE = "UPDATE student SET name=?, age=?, birthday=? WHERE id=?"


    override fun findOne(id: Int): Student? {
        var result: Student? = null
        val conn = DBFactory.getConnection()
        var statement: PreparedStatement? = null
        try {
            statement = conn!!.prepareStatement(GET)
            statement.setInt(1, id)
            var rs = statement.executeQuery()
            while (rs.next()) {
                result = fillStudent(rs)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            statement!!.close()
            conn!!.close()
        }
        return result
    }

    override fun findAll(): List<Student> {
        var result = ArrayList<Student>()
        val conn = DBFactory.getConnection()
        var statement: Statement? = null
        try {
            statement = conn!!.createStatement()
            var rs = statement.executeQuery(GET_ALL)
            while (rs.next()) {
                result.add(fillStudent(rs))
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            statement!!.close()
            conn!!.close()
        }
        return result
    }

    override fun count(): Int {
        var result = 0
        val conn = DBFactory.getConnection()
        var statement: Statement? = null
        try {
            statement = conn!!.createStatement()
            var rs = statement.executeQuery(COUNT)
            if (rs.next()) {
                result = rs.getInt(1)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            statement!!.close()
            conn!!.close()
        }
        return result
    }

    override fun save(s: Student): Student? {
        val conn = DBFactory.getConnection()
        var statement: PreparedStatement? = null
        try {
            statement = conn!!.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)
            statement.setString(1, s.name)
            statement.setInt(2, s.age)
            statement.setDate(3, Date(s.birthday.time))
            statement.setInt(4, s.storeId)
            if (statement.executeUpdate() === 0)
                return null
            val tableKeys = statement.getGeneratedKeys()
            if (tableKeys.next()) {
                return Student(
                        tableKeys.getInt(1),
                        s.name,
                        s.age,
                        s.birthday,
                        s.storeId
                )
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            statement!!.close()
            conn!!.close()
        }
        return null
    }

    override fun saveMany(students: List<Student>): Int {
        var result = 0
        val conn = DBFactory.getConnection()
        var statement: PreparedStatement? = null
        try {
            conn!!.autoCommit = false
            statement = conn.prepareStatement(SAVE)
            for (s in students) {
                statement.setString(1, s.name)
                statement.setInt(2, s.age)
                statement.setDate(3, Date(s.birthday.time))
                statement.setInt(4, s.storeId)
                statement.addBatch()
                result++
            }
            statement.executeBatch()
            conn.commit()
        } catch (e: SQLException) {
            e.printStackTrace()
            result = -1
            println("rollback")
            conn!!.rollback()
        } finally {
            statement!!.close()
            conn!!.close()
        }
        return result
    }

    override fun delete(studentId: Int): Int {
        var result = 0
        val conn = DBFactory.getConnection()
        var statement: PreparedStatement? = null
        try {
            statement = conn!!.prepareStatement(DELETE)
            statement.setInt(1, studentId)
            result = statement.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            statement!!.close()
            conn!!.close()
        }
        return result
    }

    override fun update(student: Student): Student? {
        val conn = DBFactory.getConnection()
        var statement: PreparedStatement? = null
        try {
            statement = conn!!.prepareStatement(UPDATE)
            statement.setString(1, student.name)
            statement.setInt(2, student.age)
            statement.setDate(3, Date(student.birthday.time))
            statement.setInt(4, student.id)
            if (statement.executeUpdate() == 0)
                return null
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            statement!!.close()
            conn!!.close()
        }

        return student
    }


    private fun fillStudent(rs: ResultSet): Student {
        return Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getDate("birthday"),
                rs.getInt("store_id")
        )
    }
}