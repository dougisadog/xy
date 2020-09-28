package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.viewmodel.VerifyMessageViewModel
import com.shuange.lesson.EmptyTask

open class SendVerifyCodeViewModel : VerifyMessageViewModel() {

    val phone = MutableLiveData<String>()

    fun verifyCode(onSuccess:EmptyTask) {
        val code = verifyCode.value ?: return
        onSuccess?.invoke()
    }

}