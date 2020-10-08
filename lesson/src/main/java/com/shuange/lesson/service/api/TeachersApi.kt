package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.TeachersResponse
import kotlin.reflect.KClass

/**
 * 老师列表
 */
class TeachersApi() : BaseTokenApi<TeachersResponse>() {

    override val path: String
        get() = "/api/v1.0/teachers"
    override val resultClass: KClass<TeachersResponse>
        get() = TeachersResponse::class

}
