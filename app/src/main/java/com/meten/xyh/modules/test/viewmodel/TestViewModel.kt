package com.meten.xyh.modules.test.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel

open class TestViewModel : BaseViewModel() {

    var color = MutableLiveData<String>()

    fun loadData() {
    }

}