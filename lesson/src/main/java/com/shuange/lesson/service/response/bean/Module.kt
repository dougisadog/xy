package com.shuange.lesson.service.response.bean

data class Module(
    val createdBy: String,
    val createdDate: String,
    val id: Int,
    val important: Boolean,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val lessonId: Int,
    val lessonPackageId: Int,
    val lockedStep: Int,
    val name: String,
    val sortNo: Int
)