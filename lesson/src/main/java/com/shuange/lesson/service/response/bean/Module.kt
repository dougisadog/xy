package com.shuange.lesson.service.response.bean

data class Module(
    val important: Boolean,
    val lessonId: Int,
    val lessonPackageId: Int,
    val lockedStep: Int, //TODO
    val name: String,
    val sortNo: Int
): BaseBean()