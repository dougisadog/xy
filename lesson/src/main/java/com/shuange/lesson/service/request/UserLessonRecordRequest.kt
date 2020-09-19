package com.shuange.lesson.service.request

import com.shuange.lesson.utils.RequestParam

class UserLessonRecordRequest(
    @RequestParam
    var id: Int? = null,
    @RequestParam
    var lessonId: Int = 0,
    @RequestParam
    var questionId: Int? = null,
    @RequestParam
    var score: Double? = null
)