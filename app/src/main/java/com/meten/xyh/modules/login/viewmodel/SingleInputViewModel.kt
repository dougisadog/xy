package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel

abstract class SingleInputViewModel : BaseViewModel() {

    var title = MutableLiveData<String>()

    var hint = MutableLiveData<String>()

    var input = MutableLiveData<String>()

    var button = MutableLiveData<String>()


    abstract fun initValue()

    abstract fun confirm(text:String)

}