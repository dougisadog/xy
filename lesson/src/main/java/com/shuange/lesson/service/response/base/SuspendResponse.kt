package com.shuange.lesson.service.response.base

import java.lang.Exception

class SuspendResponse<T : Any> {

    private var data: T? = null

    var exception: Exception? = null


    constructor(data: T?) {
        this.data = data
        if (null == data) {
            exception = Exception("null data")
        }
    }

    constructor(exception: Exception) {
        this.exception = exception
    }

    fun getResponse(): T? {
        return data
    }
}