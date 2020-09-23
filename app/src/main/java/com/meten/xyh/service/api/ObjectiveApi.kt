package com.meten.xyh.service.api

import com.meten.xyh.service.response.BaseSettingResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 需要提升列表
 */
class ObjectiveApi() : BaseTokenApi<BaseSettingResponse>() {
    override val path: String
            get() = "/api/v1.0/sub-users/objective/list"
    override val resultClass: KClass<BaseSettingResponse>
        get() = BaseSettingResponse::class

}
