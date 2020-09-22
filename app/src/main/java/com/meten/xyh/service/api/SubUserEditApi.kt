package com.meten.xyh.service.api

import com.meten.xyh.base.DataCache
import com.meten.xyh.service.response.SubUserResponse
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.service.api.base.BaseTokenApi
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 用户新增/编辑
 */
class SubUserEditApi(val request: SubUser) : BaseTokenApi<SubUserResponse>() {

    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/sub-users"
    override val resultClass: KClass<SubUserResponse>
        get() = SubUserResponse::class


    override fun parseResponse(data: String): SubUserResponse {
        //保存当前编辑的用户
        val index = DataCache.users.map { it.userId }.indexOf(request.userId.toString())
        if (-1 != index && index < DataCache.users.size) {
            DataCache.users[index].setSubUserData(request)
        }
        return super.parseResponse(data)
    }
}
