package com.suyang.mina.controller

import com.suyang.domain.User
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/user")
class UserController {
    @RequestMapping("/list")
    fun getUser(): List<User> {
        return listOf<User>(User(1, "张三", Date()),
                User(2, "lisi", Date()))
    }
}