package com.meten.xyh.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.base.BaseTextApiResponse
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 提醒电话 - 获取手机验证码
 */
class SendVerifyCodeForRemindApi(val phone: String) : BaseTokenApi<BaseTextApiResponse>() {

    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/users/remind-phone/$phone/token"
    override val resultClass: KClass<BaseTextApiResponse>
        get() = BaseTextApiResponse::class

}
