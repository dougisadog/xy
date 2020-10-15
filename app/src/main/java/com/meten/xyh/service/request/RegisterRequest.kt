package com.meten.xyh.service.request

import com.shuange.lesson.Storable
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.enumeration.Gender
import com.shuange.lesson.utils.RequestClass

@RequestClass
class RegisterRequest(var login: String, var password: String = "888888"): Storable {
    val appId: String = ConfigDef.APP_ID
    var base64AvatarUrl: String? = null
    var city: String? = null
    var gender: String = Gender.UNKNOWN.toString()
    var name: String = login
    var phone: String? = null
    var province: String? = null
    val secret: String = ConfigDef.APP_SECRET
    var age = 0
}
