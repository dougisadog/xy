package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.LessonDetailResponse
import kotlin.reflect.KClass

/**
 * 课程详情
 */
class LessonDetailApi(val lessonId: String) : BaseTokenApi<LessonDetailResponse>() {

    override val path: String
        get() = "/api/v1.0/lesson/lessons/$lessonId"
    override val resultClass: KClass<LessonDetailResponse>
        get() = LessonDetailResponse::class
}