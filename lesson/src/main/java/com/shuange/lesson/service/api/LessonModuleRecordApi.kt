package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.UserLessonRecordResponse
import kotlin.reflect.KClass

/**
 *模块最后记录
 */
class LessonModuleRecordApi(val lessonModuleId: String) : BaseTokenApi<UserLessonRecordResponse>() {

    override val path: String
        get() = "/api/v1.0/lesson/user-lesson-records/module/$lessonModuleId"

    override val resultClass: KClass<UserLessonRecordResponse>
        get() = UserLessonRecordResponse::class
}