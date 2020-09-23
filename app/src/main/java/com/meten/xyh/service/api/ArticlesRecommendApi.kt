package com.meten.xyh.service.api

import com.meten.xyh.service.response.ArticlesResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
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
