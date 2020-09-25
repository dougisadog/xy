package com.shuange.lesson.modules.media.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.media.bean.VideoData
import com.shuange.lesson.modules.topquality.bean.TopQualityCourseBean

open class VideoCourseViewModel : BaseViewModel() {

    var mediaData:VideoData? = null

    var content = MutableLiveData<String>()

    var courses = ObservableArrayList<TopQualityCourseBean>()

    fun loadData() {
        testData()
    }

    fun testData() {
        mediaData = VideoData().apply { setVideo("http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4") }
        content.value = "adasdasd123asdasdasdasd123sasd"
        for (i in 0 until 6) {
            courses.add(TopQualityCourseBean().apply {
                title = "topQuality$i"
                content = "topQuality content$i"
                freeType =
                    if (i == 0) null else if (i == 1) TopQualityCourseBean.FREE_TYPE_GREEN else TopQualityCourseBean.FREE_TYPE_ORANGE
            })
        }
    }

}