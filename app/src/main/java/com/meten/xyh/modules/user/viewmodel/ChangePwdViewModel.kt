package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.utils.BusinessUtil
import com.shuange.lesson.base.viewmodel.BaseViewModel

open class ChangePwdViewModel : BaseViewModel() {

    var pwd = MutableLiveData<String>()

    var newPwd = MutableLiveData<String>()

    var verified = MutableLiveData<Boolean>()

    fun changePwd() {
        if (checkPwd()) {
            startBindLaunch {
                null
            }
        }

    }

    fun checkPwd(): Boolean {
        val pwdText = pwd.value
        if (pwdText.isNullOrBlank()) {
            error.value = "原密码不能为空"
            return false
        }
        val verifyPwdText = pwd.value
        if (verifyPwdText.isNullOrBlank()) {
            error.value = "新密码不能为空"
            return false
        }
        if (pwdText == verifyPwdText) {
            error.value = "原密码新密码不能相同"
            return false
        }
        val origin = BusinessUtil.checkValidPwd(pwd.value ?: "")
        if (!origin.first) {
            error.value = origin.second
            return false
        }
        val verify = BusinessUtil.checkValidPwd(pwd.value ?: "")
        if (!verify.first) {
            error.value = verify.second
            return false
        }
        return true
    }
}