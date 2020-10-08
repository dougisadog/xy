package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.ArticlesResponse
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
