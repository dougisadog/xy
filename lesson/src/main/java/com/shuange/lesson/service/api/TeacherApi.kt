package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.TeacherResponse
import kotlin.reflect.KClass

/**
 * 名师详情（课程列表）
 */
class TeacherApi(val teacherId: String) : BaseTokenApi<TeacherResponse>() {

    override val path: String
        get() = "/api/v1.0/teachers/${teacherId}"
    override val resultClass: KClass<TeacherResponse>
        get() = TeacherResponse::class

}
