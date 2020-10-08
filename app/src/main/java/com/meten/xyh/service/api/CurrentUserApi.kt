package com.meten.xyh.service.api

import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.user.bean.UserBean
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

    override fun parseResponse(data: String): UserResponse {
        val result = super.parseResponse(data)
        result.body?.let { user ->
            DataCache.users.clear()
            //TODO 没有头像信息
            DataCache.users.add(UserBean.createUserInfo(user.subUserName, "")?.apply {
                userRecord = user
                current = true
            })
        }
        return result
    }
}
