package com.shuange.lesson.service.response.base

import com.shuange.lesson.Storable

open class BaseApiResponse<T>: Storable {
    var body: T? = null
    var code: Int = 0
    var message = ""
}