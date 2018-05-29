package com.suyang

import java.sql.Connection
import java.sql.SQLException
import java.sql.DriverManager


object DBFactory {
    private val driver = "com.mysql.cj.jdbc.Driver"
    private val url = "jdbc:mysql://localhost:3308/test02"
    private val username = "root"
    private val password = "123123"

    fun getConnection(): Connection? {
        try {
            Class.forName(driver)
            return DriverManager.getConnection(url, username, password) as Connection
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }
}