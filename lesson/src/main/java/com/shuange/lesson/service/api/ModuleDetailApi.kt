package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.ModuleDetailResponse
import kotlin.reflect.KClass

/**
 * 课程模块详情
 */
class ModuleDetailApi(val lessonModuleId: String) : BaseTokenApi<ModuleDetailResponse>() {

    override val path: String
        get() = "/api/v1.0/lesson-modules/$lessonModuleId"
    override val resultClass: KClass<ModuleDetailResponse>
        get() = ModuleDetailResponse::class
}