package com.suyang.ignite.demo

import java.sql.DriverManager
import java.lang.reflect.Array.setLong
import java.sql.PreparedStatement
import com.sun.deploy.config.JREInfo.setArgs
import org.apache.ignite.cache.query.SqlFieldsQuery
import com.sun.corba.se.impl.util.RepositoryId.cache
import org.apache.ignite.IgniteCache
import org.apache.ignite.Ignition
import org.apache.ignite.Ignite
import java.sql.ResultSet


fun main(args: Array<String>) {
    Class.forName("org.apache.ignite.IgniteJdbcThinDriver")

    query()
}

fun createTable() {
    val conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/")
    // Create database tables.
    conn.createStatement().use { stmt ->
        // Create table based on REPLICATED template.
        stmt.executeUpdate("CREATE TABLE City (" +
                " id LONG PRIMARY KEY, name VARCHAR) " +
                " WITH \"template=replicated\"")
        // Create table based on PARTITIONED template with one backup.
        stmt.executeUpdate("CREATE TABLE Person (" +
                " id LONG, name VARCHAR, city_id LONG, " +
                " PRIMARY KEY (id, city_id)) " +
                " WITH \"backups=1, affinityKey=city_id\"")
        // Create an index on the City table.
        stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)")
        // Create an index on the Person table.
        stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)")
    }
}

fun insertData() {
    // Open JDBC connection
    val conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/")
// Populate City table
    conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)").use { stmt ->
        stmt.setLong(1, 1L)
        stmt.setString(2, "Forest Hill")
        stmt.executeUpdate()
        stmt.setLong(1, 2L)
        stmt.setString(2, "Denver")
        stmt.executeUpdate()
        stmt.setLong(1, 3L)
        stmt.setString(2, "St. Petersburg")
        stmt.executeUpdate()
    }
// Populate Person table
    conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)").use { stmt ->
        stmt.setLong(1, 1L)
        stmt.setString(2, "John Doe")
        stmt.setLong(3, 3L)
        stmt.executeUpdate()
        stmt.setLong(1, 2L)
        stmt.setString(2, "Jane Roe")
        stmt.setLong(3, 2L)
        stmt.executeUpdate()
        stmt.setLong(1, 3L)
        stmt.setString(2, "Mary Major")
        stmt.setLong(3, 1L)
        stmt.executeUpdate()
        stmt.setLong(1, 4L)
        stmt.setString(2, "Richard Miles")
        stmt.setLong(3, 2L)
        stmt.executeUpdate()
    }
}

fun query() {
    // Open JDBC connection
    val conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/")
// Get data
    conn.createStatement().use { stmt ->
        stmt.executeQuery("SELECT p.name, c.name " +
                " FROM Person p, City c " +
                " WHERE p.city_id = c.id").use { rs ->
            while (rs.next())
                println(rs.getString(1) + ", " + rs.getString(2))
        }
    }
}