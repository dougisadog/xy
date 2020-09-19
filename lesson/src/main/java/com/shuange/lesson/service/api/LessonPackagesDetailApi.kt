package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.LessonPackagesResponse
import kotlin.reflect.KClass

/**
 * 课程包详情
 */
class LessonPackagesDetailApi(val lessonPackagesId: String) :
    BaseTokenApi<LessonPackagesResponse>() {

    override val path: String
        get() = "/api/v1.0/lesson/lesson-packages/$lessonPackagesId"
    override val resultClass: KClass<LessonPackagesResponse>
        get() = LessonPackagesResponse::class
}