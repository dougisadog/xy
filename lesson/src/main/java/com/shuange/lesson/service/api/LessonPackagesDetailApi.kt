package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.LessonPackagesDetailResponse
import kotlin.reflect.KClass

/**
 * 课程包详情
 */
class LessonPackagesDetailApi(val lessonPackagesId: String) :
    BaseTokenApi<LessonPackagesDetailResponse>() {

    override val path: String
        get() = "/api/v1.0/lesson-packages/$lessonPackagesId"
    override val resultClass: KClass<LessonPackagesDetailResponse>
        get() = LessonPackagesDetailResponse::class
}