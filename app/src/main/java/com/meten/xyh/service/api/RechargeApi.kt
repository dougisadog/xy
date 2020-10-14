package com.meten.xyh.service.api

import com.meten.xyh.enumeration.PayType
import com.meten.xyh.service.response.OrderResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 充值
 */
class RechargeApi(val money: Int, val type: PayType) : BaseTokenApi<OrderResponse>() {

    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/recharge-records"
    override val resultClass: KClass<OrderResponse>
        get() = OrderResponse::class

    override fun prepareRequest() {
        super.prepareRequest()
        addQuery("money", money)
        addQuery("type", type.name)

    }
}
