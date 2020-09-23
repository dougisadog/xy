package com.meten.xyh.service.request

import com.shuange.lesson.utils.RequestClass

@RequestClass
class LoginRequest(var username: String, var password: String = "888888") {
    var rememberMe = false
}