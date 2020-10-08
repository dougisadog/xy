package com.meten.xyh.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.modules.course.bean.MyCourseBean
import com.shuange.lesson.base.viewmodel.BaseViewModel

class MyCourseListViewModel : BaseViewModel() {

    val courses = ObservableArrayList<MyCourseBean>()


}