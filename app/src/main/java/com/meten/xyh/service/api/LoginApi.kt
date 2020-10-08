package com.meten.xyh.service.api

import com.meten.xyh.service.request.LoginRequest
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.base.bean.AccountBean
import com.shuange.lesson.service.api.base.BaseApi
import com.shuange.lesson.service.response.InitResponse
import com.shuange.lesson.utils.AnnotationParser
import corelib.http.PostType
import corelib.util.Log
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
        Log.e("login param", queryItems.toString())
    }

    override fun parseResponse(data: String): InitResponse {
        val result = super.parseResponse(data)
        if (!result.id_token.isBlank()) {
            LessonDataCache.token = result.id_token
            LessonDataCache.account = AccountBean().apply { id = loginRequest.login }
        }
        return result
    }
}
