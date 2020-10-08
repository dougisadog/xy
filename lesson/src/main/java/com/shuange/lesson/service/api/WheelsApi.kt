package com.shuange.lesson.service.api

import com.shuange.lesson.enumeration.WheelType
import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.WheelsResponse
import kotlin.reflect.KClass

/**
 * 轮播列表
 */
class WheelsApi(val wheelType: WheelType) : BaseTokenApi<WheelsResponse>() {

    override val path: String
        get() = "/api/v1.0/wheels"
    override val resultClass: KClass<WheelsResponse>
        get() = WheelsResponse::class

    override fun prepareRequest() {
        super.prepareRequest()
        addQuery("type.equals", wheelType.text)
    }
}
