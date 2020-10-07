package com.meten.xyh.service.api

import com.meten.xyh.service.response.AccountResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 当前账户信息
 */
class CurrentAccountInfoApi() : BaseTokenApi<AccountResponse>() {

    override val path: String
        get() = "/api/v1.0/users"
    override val resultClass: KClass<AccountResponse>
        get() = AccountResponse::class

}
