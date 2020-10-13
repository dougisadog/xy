package com.meten.xyh.service.api

import com.meten.xyh.service.response.ExchangeCodesResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 兑换记录
 */
class ExchangeCodesApi : BaseTokenApi<ExchangeCodesResponse>() {

    override val path: String
        get() = "/api/v1.0/exchange-codes"
    override val resultClass: KClass<ExchangeCodesResponse>
        get() = ExchangeCodesResponse::class

    override fun parseResponse(data: String): ExchangeCodesResponse {
        val result = super.parseResponse(data)
        return result
    }
}
