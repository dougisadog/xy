package com.meten.xyh.modules.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel

open class SearchViewModel : BaseViewModel() {

    var searchText = MutableLiveData<String>()

    var labels = mutableListOf<String>()

    init {
        labels.add("社交")
        labels.add("旅游")
        labels.add("商务")
        labels.add("职场")
        labels.add("语法")
        labels.add("雅思")
        labels.add("四六级考试")
    }

}