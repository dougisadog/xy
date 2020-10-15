package com.shuange.lesson.service.api

import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.ShortVideoTypesResponse
import kotlin.reflect.KClass

/**
 * 短视频类型
 */
class ShortVideoTypesApi() : BaseTokenApi<ShortVideoTypesResponse>() {
    override val path: String
        get() = "/api/v1.0/short-video-types"
    override val resultClass: KClass<ShortVideoTypesResponse>
        get() = ShortVideoTypesResponse::class

    override fun parseResponse(data: String): ShortVideoTypesResponse {
        val result = super.parseResponse(data)
        LessonDataCache.shortVideoTypes.clear()
        LessonDataCache.shortVideoTypes.addAll(result.body)
        return result
    }

}
