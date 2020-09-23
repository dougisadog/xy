package com.meten.xyh.service.api

import com.meten.xyh.service.response.WheelsResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 咨询轮播列表
 */
class ArticlesWheelsApi() : BaseTokenApi<WheelsResponse>() {

    override val path: String
        get() = "/api/v1.0/wheels"
    override val resultClass: KClass<WheelsResponse>
        get() = WheelsResponse::class

}
