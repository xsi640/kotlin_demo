package com.suyang.mina.dao

import com.suyang.domain.Student
import org.springframework.data.jpa.repository.JpaRepository

open interface StudentDao : JpaRepository<Student, Int>