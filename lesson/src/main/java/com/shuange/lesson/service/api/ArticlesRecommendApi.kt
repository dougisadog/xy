package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.ArticlesResponse
import kotlin.reflect.KClass

/**
 * 推荐咨询列表
 */
class ArticlesRecommendApi() : BaseTokenApi<ArticlesResponse>() {

    override val path: String
        get() = "/api/v1.0/articles/recommend"
    override val resultClass: KClass<ArticlesResponse>
        get() = ArticlesResponse::class

}
