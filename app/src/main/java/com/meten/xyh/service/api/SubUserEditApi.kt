package com.meten.xyh.service.api

import com.meten.xyh.base.DataCache
import com.meten.xyh.service.response.SubUserResponse
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.utils.AnnotationParser
import corelib.http.PostType
import corelib.util.Log
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

    override fun prepareRequest() {
        super.prepareRequest()
        request.userId = LessonDataCache.account?.id?.toLong() ?: 0L
        AnnotationParser.generateParams(this, request)
        Log.e("login param", queryItems.toString())
    }

    override fun parseResponse(data: String): SubUserResponse {
        //保存当前编辑的用户
        val index = DataCache.users.map { it.userId }.indexOf(request.userId.toString())
        if (-1 != index && index < DataCache.users.size) {
            DataCache.users[index].setSubUserData(request)
        }
        return super.parseResponse(data)
    }
}
