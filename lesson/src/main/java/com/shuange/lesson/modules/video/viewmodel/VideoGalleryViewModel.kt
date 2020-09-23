package com.shuange.lesson.modules.video.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.topquality.bean.GalleryItem
import com.shuange.lesson.modules.video.bean.VideoData
import com.shuange.lesson.service.api.ShortVideoHitApi
import com.shuange.lesson.service.api.ShortVideosApi
import com.shuange.lesson.service.api.base.suspendExecute

open class VideoGalleryViewModel : BaseViewModel() {

    var videoData = ObservableArrayList<VideoData>()

    fun loadData() {
        getVideos()
    }

    fun getVideos() {
        startBindLaunch {
            val videoId = videoData.lastOrNull()?.id ?: 0
            val suspendResult = ShortVideosApi().suspendExecute()
            val sourceData = mutableListOf<VideoData>()
            suspendResult.getResponse()?.body?.forEach {
                sourceData.add(VideoData().apply {
                    id = it.id.toString()
                    text = it.description
                    setVideo(it.imageUrl)
                    val gi = GalleryItem()
                    gi.hearts = it.hits
                    gi.pic = it.imageUrl
                    galleryItem = gi
                })
            }
            suspendResult.exception
        }

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

    fun hit(id: String) {
        ShortVideoHitApi(id).execute {

        }
    }

}