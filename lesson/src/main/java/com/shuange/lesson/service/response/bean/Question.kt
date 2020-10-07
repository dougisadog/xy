package com.shuange.lesson.service.response.bean

data class Question(
    val createdBy: String,
    val createdDate: String,
    val id: Int,
    val inputType: String,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val lessonId: Int,
    val lessonModuleId: Int,
    val lessonPackageId: Int,
    val options: List<Option>,
    val questionResourceType: String,
    val questionResourceContent: String,
    val questionResourceId: Int,
    val record: Record,
    val score: Int,
    val showAudio: Boolean,
    val showImage: Boolean,
    val showText: Boolean,
    val showVideo: Boolean,
    val sortNo: Int
) {
    var questionResourceImageUrl: String? = null
    var questionResourceAudioUrl: String? = null
    var questionResourceVideoUrl: String? = null
}