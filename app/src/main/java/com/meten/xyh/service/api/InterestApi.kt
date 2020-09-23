package com.meten.xyh.service.api

import com.meten.xyh.service.response.BaseSettingResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 兴趣列表
 */
class InterestApi() : BaseTokenApi<BaseSettingResponse>() {
    override val path: String
        get() = "/api/v1.0/sub-users/interest/list"
    override val resultClass: KClass<BaseSettingResponse>
        get() = BaseSettingResponse::class

}
