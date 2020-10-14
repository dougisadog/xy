package com.meten.xyh.service.api

import com.meten.xyh.service.response.RechargesResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 充值记录
 */
class RechargeHistoryApi() : BaseTokenApi<RechargesResponse>() {

    override val path: String
        get() = "/api/v1.0/recharge-records"
    override val resultClass: KClass<RechargesResponse>
        get() = RechargesResponse::class

}
