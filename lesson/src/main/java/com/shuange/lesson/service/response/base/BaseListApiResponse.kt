package com.shuange.lesson.service.response.base

open class BaseListApiResponse<T> {
    var body: ArrayList<T> = arrayListOf()
    var code: Int = 0
    var message = ""
}