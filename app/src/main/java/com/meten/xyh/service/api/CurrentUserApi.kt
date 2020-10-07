package com.meten.xyh.service.api

import com.meten.xyh.service.response.UserResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 获取当前用户（个人信息）
 */
class CurrentUserApi() : BaseTokenApi<UserResponse>() {

    override val path: String
        get() = "/api/v1.0/sub-users/current"
    override val resultClass: KClass<UserResponse>
        get() = UserResponse::class

}
