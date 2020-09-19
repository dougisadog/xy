package com.meten.xyh.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.modules.course.bean.MyCourseBean
import com.meten.xyh.modules.discovery.bean.TeacherBean
import com.shuange.lesson.base.viewmodel.BaseViewModel

class MyCourseViewModel : BaseViewModel() {

    val pager = mutableListOf<String>()

    val onlineCourses = ObservableArrayList<MyCourseBean>()
    val streamCourses = ObservableArrayList<MyCourseBean>()


    fun testData() {
        val img =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 3) {
            val course = MyCourseBean().apply {
                image = img
                title = "课程主题：美式发音速成课$i"
                teacher = TeacherBean("JENNY")
            }
            onlineCourses.add(course)
            streamCourses.add(course)
        }
    }

    init {
        pager.add("直播课程")
        pager.add("线上课程")
    }

    fun loadData() {
        testData()
    }
}