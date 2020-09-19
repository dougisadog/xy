package com.meten.xyh.modules.discovery.bean

data class  TeacherBean(
    var name: String = "",
    var introduction: String = "",
    var image: String = "",
    var subTitle: String = "",
    var detailContent: String = "",
    var id: String = ""

) {


    val nameText: String
        get() {
            return "主讲老师：$name"
        }
}