package com.shuange.lesson.service.api

import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.LessonTypesResponse
import kotlin.reflect.KClass

/**
 * 课程包列表分类列表
 */
class LessonTypesApi : BaseTokenApi<LessonTypesResponse>() {

    override val path: String
        get() = "/api/v1.0/lesson-types"
    override val resultClass: KClass<LessonTypesResponse>
        get() = LessonTypesResponse::class

    override fun parseResponse(data: String): LessonTypesResponse {
        val result = super.parseResponse(data)
        LessonDataCache.types.clear()
        LessonDataCache.types.addAll(result.body)
        return result
    }
}