package com.shuange.lesson.service.response.bean

data class RecordX(
    val lessonId: Int,
    val lessonModuleId: Int,
    val lessonModuleName: String,
    val lessonName: String,
    val lessonPackageId: Int,
    val lessonPackageName: String,
    val lessonType: String,
    val questionId: Int,
    val score: Int,
    val userId: Int,
    val userName: String
) : BaseBean()