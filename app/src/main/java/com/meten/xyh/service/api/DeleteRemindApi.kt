package com.meten.xyh.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.base.BaseTextApiResponse
import kotlin.reflect.KClass

/**
 * 删除提醒电话
 */
class DeleteRemindApi : BaseTokenApi<BaseTextApiResponse>() {

    init {
        httpMethod = "DELETE"
    }

    override val path: String
        get() = "/api/v1.0/users/remind-phone"
    override val resultClass: KClass<BaseTextApiResponse>
        get() = BaseTextApiResponse::class

}
