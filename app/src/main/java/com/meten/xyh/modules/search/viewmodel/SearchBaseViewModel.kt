package com.meten.xyh.modules.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.PagerItem
import com.shuange.lesson.base.viewmodel.BaseViewModel

abstract class SearchBaseViewModel : BaseViewModel() {

    var searchText = MutableLiveData<String>()
    var isEmpty = MutableLiveData<Boolean>()


    abstract val searchData: MutableList<PagerItem>

    abstract fun loadData(lastIndex: String = "0", onFinished: EmptyTask = null)

}