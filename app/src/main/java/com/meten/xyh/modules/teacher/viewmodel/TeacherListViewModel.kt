package com.meten.xyh.modules.teacher.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.bean.ActionItem
import com.meten.xyh.modules.discovery.bean.StreamLessonBean
import com.meten.xyh.modules.discovery.bean.TeacherBean
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.SourceData

class TeacherListViewModel : BaseViewModel() {

    val teachers = ObservableArrayList<TeacherBean>()

    var searchText = MutableLiveData<String>()

    fun loadData() {
        testData()
    }

    fun testData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 6) {
            teachers.add(TeacherBean().apply {
                name = "name$i"
                introduction = "introduction$i"
                image = baseImg
                subTitle = "郭老师·美式发音速成课$i"
            })
        }
    }
}