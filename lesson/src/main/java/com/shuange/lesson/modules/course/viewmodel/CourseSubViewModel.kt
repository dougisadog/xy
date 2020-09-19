package com.shuange.lesson.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.course.bean.CourseSubItem

class CourseSubViewModel : BaseViewModel() {
    val title = MutableLiveData<String>()

    val mainImg = MutableLiveData<String>()

    val pager = mutableListOf<String>()

    var content = ""

    val items = ObservableArrayList<CourseSubItem>()

    init {
        pager.add("课程列表")
        pager.add("课程介绍")
    }

    fun loadData() {
        testData()
    }

    fun testData() {
        title.value = "课程名称1"
        mainImg.value = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        content =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus pronin sapien nunc accuan eget"
        for (i in 0 until 4) {
            items.add(CourseSubItem().apply {
                name = "美式发音速成课第${i + 1}节"
            })
        }
    }
}