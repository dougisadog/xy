package com.meten.xyh.service.api

import com.meten.xyh.service.response.OrdersResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 订单列表
 */
class OrdersApi() : BaseTokenApi<OrdersResponse>() {

    override val path: String
        get() = "/api/v1.0/orders"
    override val resultClass: KClass<OrdersResponse>
        get() = OrdersResponse::class

}
