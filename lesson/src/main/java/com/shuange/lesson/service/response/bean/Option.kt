package com.shuange.lesson.service.response.bean

class Option(
    val isRight: Boolean,
    val lessonId: Long,
    val lessonModuleId: Long,
    val lessonPackageId: Long,
    val questionId: Long,
    val resourceContent: String,
    val resourceId: Long,
    val resourceType: String
) : SourceOwner()