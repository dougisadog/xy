package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.LessonPackagesResponse
import kotlin.reflect.KClass

/**
 * 课程包推荐
 */
class LessonPackagesRecommendApi : BaseTokenApi<LessonPackagesResponse>() {

    override val path: String
        get() = "/api/v1.0/lesson-packages/recommend"
    override val resultClass: KClass<LessonPackagesResponse>
        get() = LessonPackagesResponse::class
}