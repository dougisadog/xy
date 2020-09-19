package com.shuange.lesson.modules.lesson.bean

import java.io.Serializable

class Selection:Serializable {

    var text = ""
    var audio: SourceData? = null
    var img: SourceData? = null
    var bingo = false
}