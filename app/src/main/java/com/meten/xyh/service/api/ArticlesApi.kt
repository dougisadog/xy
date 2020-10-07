package com.meten.xyh.service.api

import com.meten.xyh.service.response.ArticlesResponse
import com.shuange.lesson.service.api.base.BaseTokenApi
import kotlin.reflect.KClass

/**
 * 咨询列表
 */
class ArticlesApi() : BaseTokenApi<ArticlesResponse>() {

    override val path: String
        get() = "/api/v1.0/articles"
    override val resultClass: KClass<ArticlesResponse>
        get() = ArticlesResponse::class

    fun search(text: String) {
        addQuery("title.like", text)
    }
}
