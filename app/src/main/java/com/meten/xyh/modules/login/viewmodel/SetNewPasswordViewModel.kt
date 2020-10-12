package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData

open class SetNewPasswordViewModel : SingleInputViewModel() {

    var username = MutableLiveData<String>()

    override fun initValue() {
        title.value = "设置新密码"
        hint.value = "请输入您的手机号"
        button.value = "发送验证码"
    }

    override fun confirm(text: String) {
    }
}