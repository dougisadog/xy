package com.shuange.lesson.modules.course.viewmodel

import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.base.viewmodel.BaseViewModel

class CourseAllViewModel : BaseViewModel() {

    var defaultTypeId: String? = null

    val pager = mutableListOf<Pair<String, String>>()

    fun getDefaultPageIndex(): Int {
        val typeId = defaultTypeId ?: return 0
        val target = pager.map { it.first }.indexOf(typeId)
        if (target == -1) {
            return 0
        }
        return target
    }

    fun loadData() {

    }

    fun loadTypes(onSuccess: EmptyTask) {
        pager.add(Pair(ConfigDef.COURSE_TYPE_MATCH, "赛培课程"))
        pager.add(Pair(ConfigDef.COURSE_TYPE_CHILD, "幼儿课程"))
        pager.add(Pair(ConfigDef.COURSE_TYPE_PRIMARY, "小学课程"))
        pager.add(Pair(ConfigDef.COURSE_TYPE_SENIOR_HIGH, "高中大学"))
        pager.add(Pair(ConfigDef.COURSE_TYPE_COLLEGE, "大学课程"))
        onSuccess?.invoke()
    }

}