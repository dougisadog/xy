package com.shuange.lesson.modules.topquality.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.media.bean.VideoData
import com.shuange.lesson.modules.topquality.bean.GalleryItem
import com.shuange.lesson.modules.topquality.view.GalleryFragment
import com.shuange.lesson.service.api.ShortVideosApi
import com.shuange.lesson.service.api.base.suspendExecute

class GalleryViewModel : BaseViewModel() {

    var shortVideoType: String? = null

    val galleryItems = ObservableArrayList<GalleryItem>()


    fun loadData() {
        loadGalleries("0")
    }

    fun loadGalleries(
        startId: String = galleryItems.lastOrNull()?.getItemId() ?: "0",
        page: Int = 50,
        onFinished: EmptyTask = null
    ) {
        startBindLaunch(onFinish = onFinished) {
            val suspendResult = ShortVideosApi().apply {
                addPageParam(startId)
                addShortVideo(shortVideoType ?: "")
            }.suspendExecute()
            suspendResult.getResponse()?.body?.let {
                if (startId == "0") {
                    galleryItems.clear()
                }
                val videos = mutableListOf<VideoData>()
                it.forEach {
                    val galleryItem = GalleryItem().apply {
                        setShortVideo(it)
                    }
                    galleryItems.add(galleryItem)
                    videos.add(VideoData().apply {
                        id = it.id.toString()
                        text = it.description
                        setVideo(it.imageUrl)
                        this.galleryItem = galleryItem
                    })
                }
                GalleryFragment.currentVideos = videos
            }
            suspendResult.exception
        }
//        initTest()
    }


    //TODO
    fun initTest() {
        galleryItems.clear()
        val source = mutableListOf<GalleryItem>()
        for (i in 0 until 7) {
            source.add(GalleryItem().apply {
                pic =
                    "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
                hearts = i
            })
        }
        galleryItems.addAll(source)
    }

}