package com.suyang.domain

import java.util.*

data class Student(
        val id: Int,
        var name: String,
        var age: Int,
        var birthday: Date,
        var storeId: Int
)