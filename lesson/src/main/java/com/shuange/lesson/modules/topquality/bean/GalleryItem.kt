package com.shuange.lesson.modules.topquality.bean

import com.shuange.lesson.base.PagerItem
import java.io.Serializable

class GalleryItem : Serializable, PagerItem {
    var gid = ""

    var pic: String = ""
    var hearts = 0

    val heartText: String
        get() {
            return hearts.toString()
        }

    override fun getItemId(): String {
        return gid
    }
}