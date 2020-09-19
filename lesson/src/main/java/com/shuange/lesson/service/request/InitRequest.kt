package com.shuange.lesson.service.request

import com.shuange.lesson.enumeration.Gender
import com.shuange.lesson.utils.RequestClass
import com.shuange.lesson.base.config.ConfigDef

@RequestClass
class InitRequest(var login: String) {
    val appId: String = ConfigDef.APP_ID
    var base64AvatarUrl: String? = null
    var city: String? = null
    var fromUserId: String = ""
    var gender: String? = Gender.UNKNOWN.text
    var name: String? = null
    var phone: String? = null
    var province: String? = null
    var rememberMe: Boolean = true
    val secret: String = ConfigDef.APP_SECRET
}