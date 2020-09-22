package com.meten.xyh.service.api

import com.meten.xyh.base.DataCache
import com.meten.xyh.service.response.SubUserResponse
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.service.api.base.BaseTokenApi
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 设置当前用户
 */
class SubUserSetDefaultApi(val subUserId: String) : BaseTokenApi<SubUserResponse>() {

    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/sub-users/$subUserId/current"
    override val resultClass: KClass<SubUserResponse>
        get() = SubUserResponse::class


    override fun parseResponse(data: String): SubUserResponse {
        val result = super.parseResponse(data)
        //保存当前编辑的用户
        DataCache.users.forEach { userBean ->
            userBean.current = userBean.userId == subUserId
        }
        return result
    }
}
