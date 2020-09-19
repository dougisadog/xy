package com.shuange.lesson.service.response.bean

data class Module(
    val important: Boolean,
    val lessonId: Int,
    val lessonPackageId: Int,
    val lockedStep: Int,
    val name: String,
    val sortNo: Int
): BaseBean()