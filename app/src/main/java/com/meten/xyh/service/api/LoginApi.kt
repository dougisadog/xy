package com.meten.xyh.service.api

import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.login.AccountBean
import com.meten.xyh.service.request.LoginRequest
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.service.api.base.BaseApi
import com.shuange.lesson.service.request.InitRequest
import com.shuange.lesson.service.response.InitResponse
import com.shuange.lesson.utils.AnnotationParser
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 用户登录
 */
class LoginApi(val loginRequest: LoginRequest) : BaseApi<InitResponse>() {

    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/authenticate"
    override val resultClass: KClass<InitResponse>
        get() = InitResponse::class

    override fun prepareRequest() {
        super.prepareRequest()
        AnnotationParser.generateParams(this, loginRequest)
    }

    override fun parseResponse(data: String): InitResponse {
        val result = super.parseResponse(data)
        if (result.id_token.isBlank()) {
            throw Exception(data)
        }
        LessonDataCache.token = result.id_token
        DataCache.account = AccountBean().apply { id = loginRequest.username }
        return result
    }
}
