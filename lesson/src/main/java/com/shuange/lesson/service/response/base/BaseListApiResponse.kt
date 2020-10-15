package com.shuange.lesson.service.response.base

import com.shuange.lesson.Storable

open class BaseListApiResponse<T>: Storable {
    var body: ArrayList<T> = arrayListOf()
    var code: Int = 0
    var message = ""
}