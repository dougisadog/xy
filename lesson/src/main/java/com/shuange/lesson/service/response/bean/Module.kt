package com.shuange.lesson.service.response.bean

data class Module(
    val important: Boolean,
    val lessonId: Long,
    val lessonPackageId: Long,
    val lockedStep: Int, //TODO
    val name: String,
    val sortNo: Int
) : BaseBean() {
    val lessonNo: String? = null
    val no: String? = null
    val questions: List<Question> = listOf()
    val record: ModuleRecord? = null
    val starNum: Int = 0
    val unitNo: String? = null //单元编号
}