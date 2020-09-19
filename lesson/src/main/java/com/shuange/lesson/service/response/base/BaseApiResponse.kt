package com.shuange.lesson.service.response.base

open class BaseApiResponse<T> {
    var body: T? = null
    var code: Int = 0
    var message = ""
}