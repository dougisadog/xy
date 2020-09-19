package com.shuange.lesson.service.response.bean

data class Lesson(
    val createdBy: String,
    val createdDate: String,
    val description: String,
    val id: Int,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val lessonPackageId: Int,
    val lessonType: String,
    val lockedStep: Int,
    val modules: List<Module>,
    val name: String,
    val record: Record,
    val resourceAudioUrl: String,
    val resourceId: Int,
    val resourceType: String,
    val resourceVideoUrl: String,
    val sortNo: Int
)


