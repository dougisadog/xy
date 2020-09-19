package com.shuange.lesson.service.request

import com.shuange.lesson.utils.RequestClass

@RequestClass
class InitRequest {
    val appId: String = com.shuange.lesson.base.config.ConfigDef.APP_ID
    var base64AvatarUrl: String = ""
    var city: String = ""
    var fromUserId: String = ""
    var gender: String = ""
    var login: String = ""
    var name: String = ""
    var phone: String = ""
    var province: String = ""
    var rememberMe: Boolean = true
    val secret: String = com.shuange.lesson.base.config.ConfigDef.APP_SECRET
}