package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.UserLessonRecordResponse
import kotlin.reflect.KClass

/**
 *保存课程记录 POST
 */
class UserLessonRecordApi : BaseTokenApi<UserLessonRecordResponse>() {

    override val path: String
        get() = "/api​/v1.0​/lesson​/user-lesson-records"

    override val resultClass: KClass<UserLessonRecordResponse>
        get() = UserLessonRecordResponse::class
}