package com.shuange.lesson.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.course.bean.CourseSubItem

class CourseSubListViewModel : BaseViewModel() {

    val items = ObservableArrayList<CourseSubItem>()


    fun loadData() {
        testData()
    }

    fun testData() {
        for (i in 0 until 4) {
            items.add(CourseSubItem().apply {
                name = "美式发音速成课第${i + 1}节"
            })
        }
    }
}