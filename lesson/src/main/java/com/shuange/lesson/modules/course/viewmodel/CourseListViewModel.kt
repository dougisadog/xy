package com.shuange.lesson.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.enumeration.CourseState
import com.shuange.lesson.modules.course.bean.CourseItem

class CourseListViewModel : BaseViewModel() {

    val courses = ObservableArrayList<CourseItem>()

    init {
        courses.add(CourseItem(true).apply { name = "核心课程" })
        courses.add(CourseItem().apply {
            name = "核心课程A"
            progress = 2
            state = CourseState.IN_PROGRESS
        })
        courses.add(CourseItem().apply {
            name = "核心课程B"
            progress = 3
            state = CourseState.FINISHED
        })
        courses.add(CourseItem(true).apply { name = "口语达人" })
        courses.add(CourseItem().apply {
            name = "发音"
            progress = 2
            state = CourseState.IN_PROGRESS
        })
        courses.add(CourseItem().apply {
            name = "听力"
            progress = 3
            state = CourseState.LOCKED
        })
        courses.add(CourseItem().apply {
            name = "口语"
            progress = 3
            state = CourseState.LOCKED
        })
        courses.add(CourseItem(true).apply { name = "写作达人" })
        courses.add(CourseItem().apply {
            name = "词汇"
            progress = 2
            state = CourseState.LOCKED
        })
    }

    fun loadData() {
    }
}