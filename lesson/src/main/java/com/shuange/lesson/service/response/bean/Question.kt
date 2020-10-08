package com.shuange.lesson.service.response.bean

data class Question(
    val inputType: String,
    val lessonId: Long,
    val lessonModuleId: Long,
    val lessonPackageId: Long,
    val options: List<Option>,
    val questionResourceType: String,
    val questionResourceContent: String,
    val questionResourceId: Long,
    val record: Record,
    val score: Int,
    val showAudio: Boolean,
    val showImage: Boolean,
    val showText: Boolean,
    val showVideo: Boolean,
    val sortNo: Int
) : BaseBean() {
    var questionResourceImageUrl: String? = null
    var questionResourceAudioUrl: String? = null
    var questionResourceVideoUrl: String? = null
    val userAnswer: UserAnswer? = null
}