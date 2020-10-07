package com.shuange.lesson.service.response.bean

data class ModuleDetail(
    val createdBy: String,
    val createdDate: String,
    val id: Long,
    val important: Boolean,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val lessonId: Long,
    val lessonPackageId: Long,
    val lockedStep: Int,
    val name: String,
    val questions: List<Question>,
    val record: Record,
    val sortNo: Int
)