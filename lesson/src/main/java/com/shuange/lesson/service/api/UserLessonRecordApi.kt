package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.request.UserLessonRecordRequest
import com.shuange.lesson.service.response.UserLessonRecordResponse
import com.shuange.lesson.utils.AnnotationParser
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 *保存课程记录 POST
 */
class UserLessonRecordApi(val request: UserLessonRecordRequest) :
    BaseTokenApi<UserLessonRecordResponse>() {

    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/user-lesson-records"

    override val resultClass: KClass<UserLessonRecordResponse>
        get() = UserLessonRecordResponse::class

    override fun prepareRequest() {
        super.prepareRequest()
        AnnotationParser.generateParams(this, request)
    }
}