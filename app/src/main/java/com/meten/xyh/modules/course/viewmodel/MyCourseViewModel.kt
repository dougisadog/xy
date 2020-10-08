package com.meten.xyh.modules.course.viewmodel

import com.shuange.lesson.base.viewmodel.BaseViewModel

class MyCourseViewModel : BaseViewModel() {

    val pager = mutableListOf<String>()


    init {
        pager.add("线上课程")
        pager.add("直播课程")
    }

    fun loadData() {
    }
}