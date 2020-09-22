package com.meten.xyh.service.api

import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.user.bean.UserBean
import com.meten.xyh.service.response.SubUsersResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 用户列表
 */
class SubUsersApi() : BaseTokenApi<SubUsersResponse>() {

    override val path: String
        get() = "/api/v1.0/sub-users"
    override val resultClass: KClass<SubUsersResponse>
        get() = SubUsersResponse::class


    override fun parseResponse(data: String): SubUsersResponse {
        val result = super.parseResponse(data)
        val users = result.body.mapNotNull {
            UserBean.createBySubUser(it)
        }
        DataCache.users.clear()
        DataCache.users.addAll(users)
        return result
    }
}
