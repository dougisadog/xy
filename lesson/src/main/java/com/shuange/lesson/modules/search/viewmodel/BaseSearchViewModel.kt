package com.shuange.lesson.modules.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.base.viewmodel.BaseViewModel

class BaseSearchViewModel : BaseViewModel() {

    var searchText = MutableLiveData<String>()

    var defaultTypeId: Int? = null

    val pager = mutableListOf<Pair<Int, String>>()

    init {
        pager.add(Pair(ConfigDef.TYPE_COURSE, "课程"))
        pager.add(Pair(ConfigDef.TYPE_STREAM, "直播课程"))
        pager.add(Pair(ConfigDef.TYPE_ARTICLE, "资讯"))
        pager.add(Pair(ConfigDef.TYPE_TEACHER, "老师"))
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