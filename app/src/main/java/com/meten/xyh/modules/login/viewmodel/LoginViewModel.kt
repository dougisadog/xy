package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel

open class LoginViewModel : BaseViewModel() {

    val maxTime = 60

    var username = MutableLiveData<String>()

    var verifyCode = MutableLiveData<String>()

    var confirmCheck = MutableLiveData<Boolean>()

    var remainTime = MutableLiveData<Int>()

    fun login(onSuccess:EmptyTask) {
        onSuccess?.invoke()
    }


}