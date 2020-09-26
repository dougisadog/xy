package com.meten.xyh.modules.search.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.PagerItem
import com.shuange.lesson.modules.topquality.bean.CourseBean

class SearchCourseViewModel : SearchBaseViewModel() {

    val courses = ObservableArrayList<CourseBean>()

    override val searchData: MutableList<PagerItem>
        get() {
            return courses.toMutableList()
        }

    override fun loadData(lastIndex: String, onFinished: EmptyTask) {
        if (lastIndex == "0") {
            courses.clear()
        }
//        startBindLaunch(onFinish = onFinished) {
//            suspendResult.exception
//        }
    }

}