package com.shuange.lesson.service.response.bean

data class Option(
    val createdBy: String,
    val createdDate: String,
    val id: Int,
    val isRight: Boolean,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val lessonId: Int,
    val lessonModuleId: Int,
    val lessonPackageId: Int,
    val questionId: Int,
    val resourceAudioUrl: String,
    val resourceContent: String,
    val resourceId: Int,
    val resourceImageUrl: String,
    val resourceType: String,
    val resourceVideoUrl: String
)