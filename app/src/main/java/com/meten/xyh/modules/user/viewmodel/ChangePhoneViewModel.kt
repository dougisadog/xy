package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.viewmodel.VerifyMessageViewModel
import com.meten.xyh.service.api.ChangeRemindApi
import com.meten.xyh.service.api.SendVerifyCodeForRemindApi
import com.meten.xyh.utils.BusinessUtil
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.service.api.base.suspendExecute

open class ChangePhoneViewModel : VerifyMessageViewModel() {

    var phone = MutableLiveData<String>()

    var phoneChanged = MutableLiveData<Boolean>()

    fun changePhone() {
        if (checkPhone()) {
            val phone = phone.value ?: return
            startBindLaunch {
                val suspendResult = ChangeRemindApi(phone).suspendExecute()
                if (null == suspendResult.exception) {
                    phoneChanged.value = true
                }
                suspendResult.exception
            }
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

    override fun sendMessage(onSuccess: EmptyTask) {
        val phone = phone.value ?: return
        SendVerifyCodeForRemindApi(phone).execute {
            onSuccess?.invoke()
        }
    }
}