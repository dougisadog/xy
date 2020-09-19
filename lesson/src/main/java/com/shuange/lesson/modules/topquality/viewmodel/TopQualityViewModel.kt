package com.shuange.lesson.modules.topquality.viewmodel

import com.shuange.lesson.base.viewmodel.BaseViewModel

class TopQualityViewModel : BaseViewModel() {

    val pager = mutableListOf<String>()

    init {
        pager.add("语言类")
        pager.add("听力类")
        pager.add("应用类")
        pager.add("技能类")

    }

    fun loadData() {
    }
}