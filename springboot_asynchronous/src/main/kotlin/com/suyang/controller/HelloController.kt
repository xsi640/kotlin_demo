package com.suyang.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.util.concurrent.Callable

@Controller
class IndexController {
    @GetMapping("/")
    @ResponseBody
    fun Index(): Callable<String> {
        return Callable<String> {
            Thread.sleep(5000)
            "Hello"
        }

//        return object : Callable<String> {
//            override fun call(): String {
//                Thread.sleep(5000)
//                return "Hello"
//            }
//        }
    }
}
