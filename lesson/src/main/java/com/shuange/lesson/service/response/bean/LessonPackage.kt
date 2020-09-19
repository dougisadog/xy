package com.shuange.lesson.service.response.bean

data class LessonPackage(
    val boughtDate: String,
    val description: String,
    val haveBought: Boolean,
    val lessonType: String,
    val lessons: List<Lesson>,
    val name: String,
    val price: Int,
    val record: RecordX,
    val sortNo: Int
): BaseBean()