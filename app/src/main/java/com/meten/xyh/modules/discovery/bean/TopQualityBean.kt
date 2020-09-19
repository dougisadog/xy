package com.meten.xyh.modules.discovery.bean

class TopQualityBean : BaseItemBean() {

    //0 绿 1 黄 限时免费
    var freeType: Int? = FREE_TYPE_GREEN

    companion object {
        const val FREE_TYPE_GREEN = 0
        const val FREE_TYPE_ORANGE = 1

    }
}