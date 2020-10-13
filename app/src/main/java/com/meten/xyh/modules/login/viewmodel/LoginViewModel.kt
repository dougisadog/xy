package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.viewmodel.VerifyMessageViewModel
import com.meten.xyh.service.api.CurrentAccountInfoApi
import com.meten.xyh.service.api.CurrentUserApi
import com.meten.xyh.service.api.LoginApi
import com.meten.xyh.service.api.SendVerifyCodeApi
import com.meten.xyh.service.request.LoginRequest
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

    var title = MutableLiveData<String>()

    var changeTypeName = MutableLiveData<String>()

    var username = MutableLiveData<String>()

    var password = MutableLiveData<String>()

    var confirmCheck = MutableLiveData<Boolean>()

    var isVerifyMode = MutableLiveData<Boolean>(false)


    init {
        username.value = "18341134983"
        password.value = "888888"

    }

    fun login(onSuccess: EmptyTask) {
        val username = username.value
        if (username.isNullOrBlank()) {
            error.value = "手机号不能为空"
            return
        }
        if (isVerifyMode.value == true) {
            val verifyCode = verifyCode.value
            if (verifyCode.isNullOrBlank()) {
                error.value = "验证码不能为空"
                return
            }
            loginByVerifyCode(username, verifyCode, onSuccess)
        } else {
            val password = password.value
            if (password.isNullOrBlank()) {
                error.value = "密码不能为空"
                return
            }
            loginByPassword(username, password, onSuccess)
        }

    }

    fun loginByVerifyCode(login: String, verifyCode: String, onSuccess: EmptyTask) {
        //注册
//        val suspendRegisterResult =
//            RegisterApi(RegisterRequest(username, password)).suspendExecute()
//        exception = suspendRegisterResult.exception
        loadUserData(onSuccess)
    }

    fun loginByPassword(login: String, password: String, onSuccess: EmptyTask) {
        showLoading.value = true
        startBindLaunch {
            var exception: Exception? = null
            val suspendLoginResult =
                LoginApi(LoginRequest(login, password)).suspendExecute()
            exception = suspendLoginResult.exception
            //登录
            if (null == exception) {
                loadUserData(onSuccess)
            }
//            onSuccess?.invoke()
            suspendLoginResult.exception
        }
    }

    /**
     * 获取账户信息和当期用户
     */
    fun loadUserData(onSuccess: EmptyTask) {
        startBindLaunch(onFinish = { showLoading.value = false }) {
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
                        phone = acocunt.phone ?: ""
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

    override fun sendMessage(onSuccess: EmptyTask) {
        val phone = username.value ?: return
        SendVerifyCodeApi(phone).execute {
            onSuccess?.invoke()
        }
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