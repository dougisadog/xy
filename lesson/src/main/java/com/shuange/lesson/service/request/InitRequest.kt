package com.shuange.lesson.service.request

import com.shuange.lesson.Storable
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.enumeration.Gender
import com.shuange.lesson.utils.RequestClass

@RequestClass
class InitRequest(var login: String): Storable {
    val appId: String = ConfigDef.APP_ID
    var base64AvatarUrl: String? = null
    var city: String? = null
    var fromUserId: String = ""
    var gender: String = Gender.UNKNOWN.toString()
    var name: String = login
    var phone: String? = null
    var province: String? = null
    var rememberMe: Boolean = true
    val secret: String = ConfigDef.APP_SECRET
    var age = 0
}