package com.shuange.lesson.service.request

import com.shuange.lesson.utils.RequestClass


@RequestClass
class UserLessonRecordRequest(
    val answer: String,
    val lessonModuleId: Long,
    val lessonId: Long,
    val progressIndex: Int,
    val progressTime: Int,
    val questionId: Long,
    val score: Int
)