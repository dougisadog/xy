package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseApi
import com.shuange.lesson.service.response.InitResponse
import kotlin.reflect.KClass

/**
 * 用户初始化
 */
class InitApi : BaseApi<InitResponse>() {
    override val path: String
        get() = "/api/v1.0/authenticate/init"
    override val resultClass: KClass<InitResponse>
        get() = InitResponse::class
}
