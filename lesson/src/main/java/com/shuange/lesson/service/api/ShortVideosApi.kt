package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.ShortVideosResponse
import kotlin.reflect.KClass

/**
 * 短视频列表
 */
class ShortVideosApi() : BaseTokenApi<ShortVideosResponse>() {

    override val path: String
        get() = "/api/v1.0/short-videos"
    override val resultClass: KClass<ShortVideosResponse>
        get() = ShortVideosResponse::class

    fun addShortVideo(type: String) {
        addQuery("videoType.equals", type)
    }
    fun search(type: String) {
        addQuery("videoTitle.like", type)
    }

}
