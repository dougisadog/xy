package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseApi
import com.shuange.lesson.service.request.InitRequest
import com.shuange.lesson.service.response.InitResponse
import com.shuange.lesson.utils.AnnotationParser
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 用户初始化
 */
class InitApi(val initRequest: InitRequest) : BaseApi<InitResponse>() {

    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/authenticate/init"
    override val resultClass: KClass<InitResponse>
        get() = InitResponse::class

    override fun prepareRequest() {
        super.prepareRequest()
        AnnotationParser.generateParams(this, initRequest)
    }

    override fun parseResponse(data: String): InitResponse {
        val result = super.parseResponse(data)
        if (result.id_token.isBlank()) {
            throw Exception(data)
        }
        return result
    }
}
