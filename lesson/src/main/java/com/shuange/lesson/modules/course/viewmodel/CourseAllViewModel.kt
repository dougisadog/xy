package com.shuange.lesson.modules.course.viewmodel

import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel

class CourseAllViewModel : BaseViewModel() {

    var defaultTypeId: Int? = null

    val pager = mutableListOf<Pair<Int, String>>()

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
        testTypes()
        onSuccess.hashCode()

    }

    fun testTypes() {
        pager.add(Pair(0, "赛培课程"))
        pager.add(Pair(1, "幼儿课程"))
        pager.add(Pair(2, "小学课程"))
        pager.add(Pair(3, "高中大学"))
        pager.add(Pair(4, "大学课程"))
    }
}