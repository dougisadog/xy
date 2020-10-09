package com.shuange.lesson.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.course.bean.CourseModuleItem
import com.shuange.lesson.service.api.LessonDetailApi
import com.shuange.lesson.service.api.base.suspendExecute

class CourseListViewModel : BaseViewModel() {

    var title: String = ""
    var lessonId = ""

    val modules = ObservableArrayList<CourseModuleItem>()

    val lastProcess = MutableLiveData<String>()

    var lastModuleId: Long = 0
    var lastQuestionIndex: Int = 0

    fun loadData() {
        startBindLaunch {
            val suspendResult = LessonDetailApi(lessonId).suspendExecute()
            suspendResult.getResponse()?.body?.let {
                //TODO 没有分组
                val source = it.modules
                source.sortedBy {
                    it.sortNo
                }
                val converted = source.map {
                    CourseModuleItem().apply {
                        setModule(it)
                    }
                }
                modules.clear()
                modules.addAll(converted)
                lastModuleId = it.record?.lessonModuleId?:0
                lastQuestionIndex = it.moduleRecord?.progressIndex ?: 0
                lastProcess.value = "上次学到：${it.record?.lessonModuleName?:""}"
            }
            suspendResult.exception
        }
    }

    init {
//        courses.add(CourseItem(true).apply { name = "核心课程" })
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
//        courses.add(CourseItem(true).apply { name = "口语达人" })
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
//        courses.add(CourseItem(true).apply { name = "写作达人" })
//        courses.add(CourseItem().apply {
//            name = "词汇"
//            progress = 2
//            state = CourseState.LOCKED
//        })
    }
}