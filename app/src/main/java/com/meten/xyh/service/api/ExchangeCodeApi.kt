package com.meten.xyh.service.api

import com.meten.xyh.service.response.ExchangeCodeResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 兑换课程
 */
class ExchangeCodeApi(val code: String) : BaseTokenApi<ExchangeCodeResponse>() {

    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/exchange-codes"
    override val resultClass: KClass<ExchangeCodeResponse>
        get() = ExchangeCodeResponse::class

    override fun prepareRequest() {
        super.prepareRequest()
        addQuery("exchangeCode", code)
    }

}
