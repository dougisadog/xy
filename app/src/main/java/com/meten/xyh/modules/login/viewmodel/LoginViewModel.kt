package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.viewmodel.VerifyMessageViewModel
import com.meten.xyh.service.api.*
import com.meten.xyh.service.request.LoginRequest
import com.meten.xyh.service.request.RegisterRequest
import com.meten.xyh.service.response.AccountResponse
import com.meten.xyh.service.response.UserResponse
import com.meten.xyh.utils.BusinessUtil
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.base.bean.AccountBean
import com.shuange.lesson.service.api.LessonTypesApi
import com.shuange.lesson.service.api.base.suspendExecute
import com.shuange.lesson.service.response.base.SuspendResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

open class LoginViewModel : VerifyMessageViewModel() {

    var username = MutableLiveData<String>()

    var password = MutableLiveData<String>()

    var confirmCheck = MutableLiveData<Boolean>()

    init {
        username.value = "18341134983"
        password.value = "888888"

    }

    fun testLogin(onSuccess: EmptyTask) {
//        startBindLaunch {
//            val request = InitRequest("dougisadog")
//            val suspendResult = InitApi(request).suspendExecute()
//            LessonDataCache.token = suspendResult.getResponse()?.id_token ?: ""
//            onSuccess?.invoke()
//            suspendResult.exception
//        }
        login(onSuccess)
    }

    fun login(onSuccess: EmptyTask) {
        showLoading.value = true
        startBindLaunch {
            var exception: Exception?
            val username = username.value ?: ""
            val password = password.value ?: ""

//            var suspendLoginResult =
//                LoginApi(LoginRequest(username, password)).suspendExecute()
//            exception = suspendLoginResult.exception
            //未登录
            exception = java.lang.Exception("")
            if (null != exception) {
                //注册
                val suspendRegisterResult =
                    RegisterApi(RegisterRequest(username, password)).suspendExecute()
                exception = suspendRegisterResult.exception
                //登录
                if (null == exception) {
                    suspendRegisterResult.getResponse()?.body?.let {
                        val suspendLoginResult =
                            LoginApi(LoginRequest(username, password)).suspendExecute()
                    }
                    exception = suspendRegisterResult.exception
                }
            }
            loadUserData(onSuccess)
//            onSuccess?.invoke()
            exception
        }
    }

    /**
     * 获取账户信息和当期用户
     */
    fun loadUserData(onSuccess: EmptyTask) {
        startBindLaunch(onFinish = {showLoading.value = false}) {
            val tasks = arrayListOf(
                async { CurrentAccountInfoApi().suspendExecute() },
                async { CurrentUserApi().suspendExecute() },
                async { LessonTypesApi().suspendExecute() }
            )
            val results = tasks.awaitAll()
            var exception: Exception? = null
            var accountValid = false
            var currentUserValid = false

            (results[0] as? SuspendResponse<AccountResponse>)?.let {
                exception = it.exception
                it.getResponse()?.body?.let { acocunt ->
                    LessonDataCache.account = AccountBean().apply {
                        id = acocunt.id.toString()
                        phone = acocunt.phone?:""
                        xyBalance = acocunt.money
                    }
                    accountValid = true
                }
            }
            (results[1] as? SuspendResponse<UserResponse>)?.let {
                if (null == exception) {
                    exception = it.exception
                }
                currentUserValid = true
            }
            if (accountValid && currentUserValid && null == exception) {
                onSuccess?.invoke()
            }
            exception

        }
    }

    override fun sendMessage() {
        val phone = username.value ?: return
        SendVerifyCodeApi(phone).execute()
    }

    fun checkPhone(): Boolean {
        val phoneText = username.value
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