package com.shuange.lesson.service.response.bean

data class Lesson(
    val description: String,
    val lessonPackageId: Long,
    val lessonType: String,
    val lockedStep: Int,
    val modules: List<Module>,
    val name: String,
    val record: Record,
    val resourceId: Long,
    val resourceType: String,
    val sortNo: Int
) : SourceOwner() {
    val lessonMode: String = "" //课程模式 LessonPackageType
    val lessonNo: String = "" //课程编号
    val resourceContent: String = "" //资源内容
    val starNum: Int = 0
    val unitNo: String = "" //单元编号
}


