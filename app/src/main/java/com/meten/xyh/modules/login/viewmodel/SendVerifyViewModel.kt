package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.viewmodel.VerifyMessageViewModel
import com.meten.xyh.service.api.SendVerifyCodeApi

open class SendVerifyViewModel : VerifyMessageViewModel() {

    var username = MutableLiveData<String>()


    var verified = MutableLiveData<Boolean>()

    override fun sendMessage() {
        val phone = username.value ?: return
        SendVerifyCodeApi(phone).execute()
    }

    fun verify() {
        val code = verifyCode.value
        verified.value = true
    }

}