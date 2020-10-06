package com.shuange.lesson.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.course.bean.CourseItem
import com.shuange.lesson.modules.topquality.bean.CourseBean
import com.shuange.lesson.service.api.LessonPackagesDetailApi
import com.shuange.lesson.service.api.base.suspendExecute

class CourseViewModel : BaseViewModel() {
    var courseBean:CourseBean? = null
    val lastProcess = MutableLiveData<String>()


    val mainImg = MutableLiveData<String>()
    val courses = ObservableArrayList<CourseItem>()


    fun loadData() {
        val courseBean = courseBean?:return
        val lessonPackagesId = courseBean.courseId
        testData()
        startBindLaunch {
            val suspendResult = LessonPackagesDetailApi(lessonPackagesId).suspendExecute()
            suspendResult.getResponse()?.body?.let {
               val source =  it.lessons
                source.sortedBy { it.sortNo }
                val converted = source.map {
                    CourseItem().apply {
                        setLesson(it)
                    }
                }
                courses.clear()
                courses.addAll(converted)
            }
            suspendResult.exception
        }
    }

    fun testData() {
        mainImg.value = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        lastProcess.value = "上次学到：4.学问位置》核心课程A"
//        courses.add(CourseItem().apply {
//            name = "核心课程A"
//            progress = 2
//            state = CourseState.IN_PROGRESS
//        })
//        courses.add(CourseItem().apply {
//            name = "核心课程B"
//            progress = 3
//            state = CourseState.FINISHED
//        })
//        courses.add(CourseItem().apply {
//            name = "发音"
//            progress = 2
//            state = CourseState.IN_PROGRESS
//        })
//        courses.add(CourseItem().apply {
//            name = "听力"
//            progress = 3
//            state = CourseState.LOCKED
//        })
//        courses.add(CourseItem().apply {
//            name = "口语"
//            progress = 3
//            state = CourseState.LOCKED
//        })
//        courses.add(CourseItem().apply {
//            name = "词汇"
//            progress = 2
//            state = CourseState.LOCKED
//        })
    }
}