package com.meten.xyh.service.response.bean

import com.shuange.lesson.service.response.bean.BaseBean
import com.shuange.lesson.service.response.bean.LessonPackage

class Teacher : BaseBean() {
    val description: String = ""
    val hits: Int = 0
    val imageUrl: String = ""
    val info: String = ""
    val lessonPackages: List<LessonPackage> = arrayListOf()
    val name: String = ""
    val recommend: Boolean = false
    val sortNo: Int = 0
}