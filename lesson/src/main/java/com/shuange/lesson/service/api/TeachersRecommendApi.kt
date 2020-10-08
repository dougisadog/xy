package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.TeachersResponse
import kotlin.reflect.KClass

/**
 * 老师推荐列表
 */
class TeachersRecommendApi() : BaseTokenApi<TeachersResponse>() {

    override val path: String
        get() = "/api/v1.0/teachers/recommend"
    override val resultClass: KClass<TeachersResponse>
        get() = TeachersResponse::class

}
