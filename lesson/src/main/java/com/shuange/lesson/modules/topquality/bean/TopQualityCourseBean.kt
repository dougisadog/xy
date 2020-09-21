package com.shuange.lesson.modules.topquality.bean

import com.shuange.lesson.base.BaseItemBean

class TopQualityCourseBean : BaseItemBean() {

    //0 绿 1 黄 限时免费 2直播付费 3已购
    var freeType: Int? = FREE_TYPE_GREEN

    companion object {
        const val FREE_TYPE_GREEN = 0
        const val FREE_TYPE_ORANGE = 1

        const val PAY_TYPE_STEAM = 2
        const val PAID_TYPE = 3

    }
}