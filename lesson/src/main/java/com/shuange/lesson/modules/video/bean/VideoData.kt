package com.shuange.lesson.modules.video.bean

import com.shuange.lesson.modules.lesson.bean.SourceData
import com.shuange.lesson.modules.topquality.bean.GalleryItem
import java.io.Serializable

class VideoData:Serializable {

    var id = ""

    var source: SourceData? = null

    var text = ""

    var galleryItem: GalleryItem? = null

    fun setVideo(link: String) {
        source = buildSourceDataByLink(link, "video_$id")
    }

    private fun buildSourceDataByLink(
        link: String,
        id: String,
        dic: String = "videos"
    ): SourceData {
        return SourceData().apply {
            name = id
            dictionary = dic
            url = link

            val arr = link.split(".")
            suffix = if (arr.size > 1) arr[arr.size - 1] else ""
        }
    }
}