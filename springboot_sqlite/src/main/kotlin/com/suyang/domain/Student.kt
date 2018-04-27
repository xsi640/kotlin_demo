package com.suyang.mina.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Student(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int,
        var name: String,
        var age: Int,
        var birthday: Date
){
        constructor():this(0, "", 0 , Date())
}