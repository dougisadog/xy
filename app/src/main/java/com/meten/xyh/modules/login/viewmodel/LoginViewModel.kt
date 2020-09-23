package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.service.api.LoginApi
import com.meten.xyh.service.api.RegisterApi
import com.meten.xyh.service.api.SubUsersApi
import com.meten.xyh.service.request.LoginRequest
import com.meten.xyh.service.request.RegisterRequest
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute
import java.lang.Exception

open class LoginViewModel : BaseViewModel() {

    val maxTime = 60

    var username = MutableLiveData<String>()

    var verifyCode = MutableLiveData<String>()

    var confirmCheck = MutableLiveData<Boolean>()

    var remainTime = MutableLiveData<Int>()

    fun login(onSuccess: EmptyTask) {
        startBindLaunch {
            var exception: Exception?
            var suspendLoginResult =
                LoginApi(LoginRequest(username.value ?: "")).suspendExecute()
            exception = suspendLoginResult.exception
            //未登录
            if (null != exception) {
                //注册
                val suspendRegisterResult =
                    RegisterApi(RegisterRequest(username.value ?: "")).suspendExecute()
                exception = suspendRegisterResult.exception
                //登录
                if (null == exception) {
                    suspendRegisterResult.getResponse()?.body?.let {
                        suspendLoginResult =
                            LoginApi(LoginRequest(username.value ?: "")).suspendExecute()
                    }
                    exception = suspendRegisterResult.exception
                }
            }
            if (null == exception) {
                //用户列表
                val suspendSubUsersResult = SubUsersApi().suspendExecute()
                exception = suspendSubUsersResult.exception
            }
            onSuccess?.invoke()
            exception
        }
    }
}