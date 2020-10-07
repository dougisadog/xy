package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.viewmodel.VerifyMessageViewModel
import com.meten.xyh.service.api.ChangeRemindApi
import com.meten.xyh.service.api.SendVerifyCodeForRemindApi
import com.meten.xyh.utils.BusinessUtil

open class ChangePhoneViewModel : VerifyMessageViewModel() {

    var phone = MutableLiveData<String>()

    fun changePhone() {
        if (checkPhone()) {
            ChangeRemindApi(phone.value ?: return).execute()
        }
    }

    fun sendVerifyMessage() {
        if (checkPhone()) {
            sendVerifyCode()
        }
    }

    fun checkPhone(): Boolean {
        val phoneText = phone.value
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

    override fun sendMessage() {
        val phone = phone.value ?: return
        SendVerifyCodeForRemindApi(phone).execute()
    }
}