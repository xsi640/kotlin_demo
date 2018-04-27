package com.suyang.mina.domain

import com.alibaba.fastjson.annotation.JSONField
import java.util.*

class User(
        var id: Int = 0,
        var name: String = "",
        @JSONField(serialize = false)
        var birthday: Date
)