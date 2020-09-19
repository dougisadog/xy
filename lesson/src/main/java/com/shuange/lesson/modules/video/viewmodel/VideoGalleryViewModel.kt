package com.shuange.lesson.modules.video.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.topquality.bean.GalleryItem
import com.shuange.lesson.modules.video.bean.VideoData

open class VideoGalleryViewModel : BaseViewModel() {

    var videoData = ObservableArrayList<VideoData>()

    fun loadData() {
        getVideos()
    }

    fun getVideos() {
        val test = mutableListOf<VideoData>()
        for (i in 0 until 7) {
            val data = VideoData()
            data.setVideo("http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4")
            data.galleryItem = GalleryItem().apply {
                hearts = i
            }
            data.text = "text $i"
            test.add(data)
        }
        videoData.addAll(test)
    }

}