package com.shuange.lesson.modules.course.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.shuange.lesson.enumeration.CourseState

class CourseItem(private val isTitle: Boolean = false) : MultiItemEntity {

    companion object {
        const val COURSE_TITLE = 1
        const val COURSE_ITEM = 2

    }

    var courseType: String = ""

    var courseId = ""

    var name = ""

    var progress = 0

    var state: CourseState? = null

    override fun getItemType(): Int {
        return if (isTitle) COURSE_TITLE else COURSE_ITEM
    }
}