package com.meten.xyh.modules.discovery.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.modules.discovery.bean.*
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.viewmodel.BaseViewModel

open class DiscoveryViewModel : BaseViewModel() {

    var streamLessons = ObservableArrayList<StreamLessonBean>()

    var teachers = ObservableArrayList<TeacherBean>()

    var topQualityItems = ObservableArrayList<TopQualityBean>()

    var newsItems = ObservableArrayList<BaseItemBean>()

    fun loadData() {
        initTestData()
    }

    fun initTestData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 3) {
            streamLessons.add(StreamLessonBean().apply {
                title = "stream$i"
                teacher = "stream teacher$i"
                snapshot = baseImg

            })
            teachers.add(TeacherBean().apply {
                name = "name$i"
                introduction = "introduction$i"
                image = baseImg
            })

            topQualityItems.add(TopQualityBean().apply {
                title = "topQuality$i"
                content = "topQuality content$i"
                image = baseImg
                freeType = if (i == 0) null else if (i == 1) TopQualityBean.FREE_TYPE_GREEN else TopQualityBean.FREE_TYPE_ORANGE
            })
            newsItems.add(NewsBean().apply {
                title = "news$i"
                content = "news content$i"
                image = baseImg
            })
        }

    }

}