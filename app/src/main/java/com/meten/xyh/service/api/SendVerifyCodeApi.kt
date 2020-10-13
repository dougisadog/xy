package com.meten.xyh.service.api

import com.shuange.lesson.service.api.base.BaseApi
import com.shuange.lesson.service.response.base.BaseTextApiResponse
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 登录、注册 - 获取手机验证码
 */
class SendVerifyCodeApi(val phone: String) : BaseApi<BaseTextApiResponse>() {

    init {
        post(PostType.JSON)
    }
    override val path: String
        get() = "/api/v1.0/authenticate/$phone/token"
    override val resultClass: KClass<BaseTextApiResponse>
        get() = BaseTextApiResponse::class

}
