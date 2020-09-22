package com.meten.xyh.service.request

import com.shuange.lesson.utils.RequestClass

@RequestClass
class LoginRequest(var username: String, var password: String) {
    var rememberMe = false
}