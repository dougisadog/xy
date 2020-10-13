package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.viewmodel.VerifyMessageViewModel
import com.meten.xyh.service.api.SendVerifyCodeApi
import com.shuange.lesson.EmptyTask

open class SendVerifyViewModel : VerifyMessageViewModel() {

    var username = MutableLiveData<String>()


    var verified = MutableLiveData<Boolean>()

    override fun sendMessage(onSuccess:EmptyTask) {
        val phone = username.value ?: return
        SendVerifyCodeApi(phone).execute {
            onSuccess?.invoke()
        }
    }

    fun verify() {
        val code = verifyCode.value
        verified.value = true
    }

}