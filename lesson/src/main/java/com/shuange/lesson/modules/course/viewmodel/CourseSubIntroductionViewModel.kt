package com.shuange.lesson.modules.course.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel

class CourseSubIntroductionViewModel : BaseViewModel() {

    val introduction = MutableLiveData<String>()

    fun loadData() {
    }
}