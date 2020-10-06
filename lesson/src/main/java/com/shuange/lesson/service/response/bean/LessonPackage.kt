package com.shuange.lesson.service.response.bean

data class LessonPackage(
    val boughtDate: String,
    val description: String,
    val haveBought: Boolean, //免费 只有黄色显示免费
    val lessonType: String,
    val lessons: List<Lesson>,
    val name: String,
    val price: Int,
    val record: Record,
    val sortNo: Int
): BaseBean()