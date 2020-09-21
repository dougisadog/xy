package com.shuange.lesson.base

open class BaseItemBean(
    var title: String = "",
    var content: String = "",
    var image: String = ""
) {
    var teacherName = ""
    var courseCount = 0

    val teacherText:String
    get() {
        return "主讲老师：$teacherName"
    }

    val courseCountText:String
        get() {
            return "课时：$courseCount"
        }
}