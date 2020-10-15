package com.shuange.lesson.service.request

import com.shuange.lesson.Storable
import com.shuange.lesson.utils.RequestClass


@RequestClass
class UserLessonRecordRequest(
    val answer: String,
    val lessonModuleId: Long,
    val lessonId: Long,
    val lessonPackageId: Long,
    val progressIndex: Int,
    val progressTime: Int,
    val questionId: Long,
    val score: Int
): Storable