package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.base.BaseStringApiResponse
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 短视频列表
 */
class ShortVideoHitApi(val shortVideoId: String) : BaseTokenApi<BaseStringApiResponse>() {
    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/short-videos/$shortVideoId/hits"
    override val resultClass: KClass<BaseStringApiResponse>
        get() = BaseStringApiResponse::class

}
