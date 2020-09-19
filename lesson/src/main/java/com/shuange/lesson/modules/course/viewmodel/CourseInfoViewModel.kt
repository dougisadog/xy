package com.shuange.lesson.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.enumeration.CourseState
import com.shuange.lesson.modules.course.bean.CourseInfoItem
import com.shuange.lesson.modules.course.bean.CourseItem
import com.shuange.lesson.service.api.LessonPackagesApi
import com.shuange.lesson.service.api.base.suspendExecute

class CourseInfoViewModel : BaseViewModel() {
    val title = MutableLiveData<String>()
    val mainImg = MutableLiveData<String>()
    val courses = ObservableArrayList<CourseInfoItem>()

    init {
        title.value = "title1"
        mainImg.value =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
//        for (i in 0 until 7) {
//            courses.add(CourseInfoItem().apply {
//                title = "初级课程$i"
//                introduction = "4.学问位置》核心课程A"
//                image =
//                    "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
//            })
//        }
    }

    fun loadData() {
        startBindLaunch {
            val result = LessonPackagesApi().suspendExecute()
            result.getResponse()?.body?.let {
                val arr = it
                arr.sortedBy {
                    it.sortNo
                }
                val converted = arr.map { lp ->
                    CourseInfoItem().apply {
                        setLessonPackages(lp)
                    }
                }
                courses.clear()
                courses.addAll(converted)
            }
            result.exception
        }
    }
}