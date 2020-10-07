package com.meten.xyh.service.response.bean

import com.shuange.lesson.service.response.bean.BaseBean

class Wheel : BaseBean() {
    var appId: String = ""
    var appInfoId: Long = 0
    var appInfoName: String = ""
    var content: String = ""
    var enabled: Boolean = true
    var hits: Int = 0
    var imageUrl: String = ""
    var recommend: Boolean = false
    var sortNo: Int = 0
    var subTitle: String = ""
    var title: String = ""
}