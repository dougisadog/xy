package com.meten.xyh.service.api

import com.meten.xyh.service.response.StagesResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 阶段列表
 */
class StagesApi() : BaseTokenApi<StagesResponse>() {
    override val path: String
        get() = "/api/v1.0/sub-users/stage/list"
    override val resultClass: KClass<StagesResponse>
        get() = StagesResponse::class

}
