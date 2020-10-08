package com.shuange.lesson.modules.teacher.bean

import com.shuange.lesson.base.PagerItem
import com.shuange.lesson.service.response.bean.Teacher

data class  TeacherBean(
    var name: String = "",
    var introduction: String = "",
    var image: String = "",
    var subTitle: String = "",
    var detailContent: String = "",
    var id: String = ""

): PagerItem {


    val nameText: String
        get() {
            return "主讲老师：$name"
        }

    fun setTeacher(teacher: Teacher) {
        name = teacher.name
        subTitle = teacher.description
        introduction = teacher.info
        image = teacher.imageUrl
    }

    override fun getItemId(): String {
        return id
    }
}