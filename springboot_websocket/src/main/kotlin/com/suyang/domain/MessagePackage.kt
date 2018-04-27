package com.suyang.mina.domain

import java.io.Serializable
import java.util.*

class MessagePackage : Serializable {

    var message: String
    var time: Date

    constructor(message: String, time: Date = Date()) {
        this.message = message
        this.time = time
    }
}
