package com.shuange.lesson.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.course.bean.MyCourseBean

class MyCourseListViewModel : BaseViewModel() {

    val courses = ObservableArrayList<MyCourseBean>()


}