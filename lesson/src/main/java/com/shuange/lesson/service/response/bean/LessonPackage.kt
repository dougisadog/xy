package com.shuange.lesson.service.response.bean

data class LessonPackage(
    val boughtDate: String,
    val description: String,
    val haveBought: Boolean, //免费 只有黄色显示免费
    val lessonType: String,
    val lessonMode: String,
    val lessons: List<Lesson>,
    val name: String,
    val price: Int,
    val sortNo: Int
) : BaseBean() {
    val duration: Int = 0
    val imageUrl: String? = null
    val no: String? = null //编号
    val recommend: Boolean = false
    val teacherId: Long = 0
    val teacherName: String = ""
    var hits: Int = 0
    var record: Record? = null
    val moduleRecord: ModuleRecord? = null
}
