package com.shuange.lesson.modules.media.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.media.bean.VideoData
import com.shuange.lesson.modules.topquality.bean.GalleryItem
import com.shuange.lesson.modules.topquality.view.GalleryFragment
import com.shuange.lesson.service.api.ShortVideoHitApi

open class VideoGalleryViewModel : BaseViewModel() {

    var defaultId:String? = null

    var videoData = ObservableArrayList<VideoData>()

    fun loadData() {
        getVideos()
    }

    fun getVideos() {
        videoData.clear()
        videoData.addAll(GalleryFragment.currentVideos)
//        startBindLaunch {
//            val videoId = videoData.lastOrNull()?.id ?: 0
//            val suspendResult = ShortVideosApi().suspendExecute()
//            val sourceData = mutableListOf<VideoData>()
//            suspendResult.getResponse()?.body?.forEach {
//                sourceData.add(VideoData().apply {
//                    id = it.id.toString()
//                    text = it.description
//                    setVideo(it.imageUrl)
//                    val gi = GalleryItem()
//                    gi.hearts = it.hits
//                    gi.pic = it.imageUrl
//                    galleryItem = gi
//                })
//            }
//            suspendResult.exception
//        }
//        testData() {
    }

    fun testData() {
        videoData.clear()
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