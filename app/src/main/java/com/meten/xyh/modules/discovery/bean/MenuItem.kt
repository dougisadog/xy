package com.meten.xyh.modules.discovery.bean

import com.shuange.lesson.EmptyTask

data class MenuItem(
    var title: String = "",
    var imageSource: Int,
    var onClick: EmptyTask
) {


}