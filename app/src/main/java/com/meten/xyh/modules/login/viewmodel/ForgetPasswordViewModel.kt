package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.utils.BusinessUtil

open class ForgetPasswordViewModel : SingleInputViewModel() {

    var confirmed = MutableLiveData<Boolean>()
    override fun initValue() {
        title.value = "设置新密码"
        hint.value = "请输入您的手机号"
        button.value = "发送验证码"
    }

    override fun confirm(text: String) {
        if (checkPhone()) {
            confirmed.value = true
        }
    }

    fun checkPhone(): Boolean {
        val phoneText = input.value
        if (phoneText.isNullOrBlank()) {
            error.value = "手机号不能为空"
            return false
        }
        val p = BusinessUtil.checkValidPhone(phoneText)
        return if (p.first) {
            true
        } else {
            error.value = p.second
            false
        }
    }
}