package com.meten.xyh.service.request

import com.shuange.lesson.Storable
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.utils.RequestClass

@RequestClass
class LoginRequest(var login: String, var password: String = ""):Storable {
    var rememberMe = false
    val appId: String = ConfigDef.APP_ID
    val secret: String = ConfigDef.APP_SECRET
    var token:String = ""
}