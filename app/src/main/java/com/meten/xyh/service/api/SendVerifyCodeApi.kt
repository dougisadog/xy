package com.meten.xyh.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.base.BaseTextApiResponse
import kotlin.reflect.KClass

/**
 * 登录、注册 - 获取手机验证码
 */
class SendVerifyCodeApi(val phone: String) : BaseTokenApi<BaseTextApiResponse>() {
    override val path: String
        get() = "/api/v1.0/authenticate/$phone/token"
    override val resultClass: KClass<BaseTextApiResponse>
        get() = BaseTextApiResponse::class

}
