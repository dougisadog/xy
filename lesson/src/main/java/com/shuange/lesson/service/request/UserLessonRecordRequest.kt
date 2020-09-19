package com.shuange.lesson.service.request

import com.shuange.lesson.utils.RequestParam

class UserLessonRecordRequest(
    @RequestParam
    var id: Int,

    var lessonId: Int = 0,
    var questionId: Int = 0,
    var score: Int = 0
)