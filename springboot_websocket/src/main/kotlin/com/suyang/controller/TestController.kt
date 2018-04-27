package com.suyang.mina.controller

import com.suyang.domain.MessagePackage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController {

    @GetMapping("/")
    fun index(): String {
        return "/index"
    }

    @MessageMapping("/send")
    @SendTo("/topic/msg")
    fun send(message: MessagePackage): MessagePackage {
        System.out.println("receive msg:" + message.message)
        return MessagePackage(message.message + "...ok")
    }
}