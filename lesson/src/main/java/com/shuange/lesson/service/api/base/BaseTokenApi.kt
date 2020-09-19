package com.shuange.lesson.service.api.base

import com.shuange.lesson.base.LessonDataCache


abstract class BaseTokenApi<DataType : Any> : BaseApi<DataType>() {

    override val headers: Map<String, String>
        get() = mapOf(Pair("Authorization", "Bearer ${LessonDataCache.token}"))
}
