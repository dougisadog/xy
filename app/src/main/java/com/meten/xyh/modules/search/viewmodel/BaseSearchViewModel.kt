package com.meten.xyh.modules.search.viewmodel

import com.shuange.lesson.base.viewmodel.BaseViewModel

class BaseSearchViewModel : BaseViewModel() {

    var defaultTypeId: Int? = null

    val pager = mutableListOf<Pair<Int, String>>()

    init {
        pager.add(Pair(0, "课程"))
        pager.add(Pair(1, "直播课程"))
        pager.add(Pair(2, "资讯"))
        pager.add(Pair(3, "老师"))
    }

    fun getDefaultPageIndex(): Int {
        val typeId = defaultTypeId ?: return 0
        val target = pager.map { it.first }.indexOf(typeId)
        if (target == -1) {
            return 0
        }
        return target
    }

    fun loadData() {

    }

}