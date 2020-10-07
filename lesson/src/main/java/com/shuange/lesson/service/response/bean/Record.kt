package com.shuange.lesson.service.response.bean

data class Record(
    val lessonId: Long,
    val lessonModuleId: Long,
    val lessonModuleName: String,
    val lessonName: String,
    val lessonPackageId: Long,
    val lessonPackageName: String,
    val lessonType: String,
    val questionId: Long,
    val score: Int,
    val userId: Long,
    val userName: String
): BaseBean()
