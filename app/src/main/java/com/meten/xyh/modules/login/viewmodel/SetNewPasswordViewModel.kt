package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData

open class SetNewPasswordViewModel : SingleInputViewModel() {

    var username = MutableLiveData<String>()

    override fun initValue() {
        title.value = "设置新密码"
        hint.value = "密码至少8位字母/数字组合"
        button.value = "完成"
    }

    override fun confirm(text: String) {
    }
}