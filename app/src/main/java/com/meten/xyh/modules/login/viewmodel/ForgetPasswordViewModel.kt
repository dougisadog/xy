package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.utils.BusinessUtil

open class ForgetPasswordViewModel : SingleInputViewModel() {

    var confirmed = MutableLiveData<Boolean>()
    override fun initValue() {
        title.value = "设置新密码"
        hint.value = "密码至少8位字母/数字组合"
        button.value = "完成"
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