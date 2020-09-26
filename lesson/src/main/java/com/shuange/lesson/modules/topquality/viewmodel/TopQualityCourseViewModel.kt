package com.shuange.lesson.modules.topquality.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.topquality.bean.CourseBean

class TopQualityCourseViewModel : BaseViewModel() {

    var topQualityItems = ObservableArrayList<CourseBean>()

    fun loadData() {
        initTestData()
    }

    fun initTestData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 3) {

            topQualityItems.add(CourseBean().apply {
                title = "topQuality$i"
                content = "topQuality content$i"
                image = baseImg
                freeType =
                    if (i == 0) null else if (i == 1) CourseBean.PAY_TYPE_STEAM else CourseBean.PAID_TYPE
                courseCount = i
                teacherName = "teacher $i"
            })
        }
    }

}