package com.shuange.lesson.modules.topquality.bean

import java.io.Serializable

class GalleryItem : Serializable {

    var pic: String = ""
    var hearts = 0

    val heartText: String
        get() {
            return hearts.toString()
        }
}