package com.meten.xyh.service.api

import com.meten.xyh.service.request.RegisterRequest
import com.meten.xyh.service.response.RegisterResponse
import com.shuange.lesson.service.api.base.BaseApi
import com.shuange.lesson.utils.AnnotationParser
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 用户注册
 */
class RegisterApi(val registerRequest: RegisterRequest) : BaseApi<RegisterResponse>() {

    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/register"
    override val resultClass: KClass<RegisterResponse>
        get() = RegisterResponse::class

    override fun prepareRequest() {
        super.prepareRequest()
        AnnotationParser.generateParams(this, registerRequest)
    }

    override fun parseResponse(data: String): RegisterResponse {
        val result = super.parseResponse(data)
        return result
    }
}
