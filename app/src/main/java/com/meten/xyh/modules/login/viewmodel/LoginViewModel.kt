package com.meten.xyh.modules.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.viewmodel.VerifyMessageViewModel
import com.meten.xyh.modules.login.AccountBean
import com.meten.xyh.modules.user.bean.UserBean
import com.meten.xyh.service.api.*
import com.meten.xyh.service.request.LoginRequest
import com.meten.xyh.service.request.RegisterRequest
import com.meten.xyh.service.response.AccountResponse
import com.meten.xyh.service.response.UserResponse
import com.meten.xyh.utils.BusinessUtil
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.service.api.base.suspendExecute
import com.shuange.lesson.service.response.base.SuspendResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

open class LoginViewModel : VerifyMessageViewModel() {

    var username = MutableLiveData<String>()

    var confirmCheck = MutableLiveData<Boolean>()

    fun login(onSuccess: EmptyTask) {
        startBindLaunch {
            var exception: Exception?
            val username = username.value ?: ""
            var suspendLoginResult =
                LoginApi(LoginRequest(username)).suspendExecute()
            exception = suspendLoginResult.exception
            //未登录
            if (null != exception) {
                //注册
                val suspendRegisterResult =
                    RegisterApi(RegisterRequest(username)).suspendExecute()
                exception = suspendRegisterResult.exception
                //登录
                if (null == exception) {
                    suspendRegisterResult.getResponse()?.body?.let {
                        suspendLoginResult =
                            LoginApi(LoginRequest(username)).suspendExecute()
                    }
                    exception = suspendRegisterResult.exception
                }
            }
//            if (null == exception) {
//                //用户列表
//                val suspendSubUsersResult = SubUsersApi().suspendExecute()
//                exception = suspendSubUsersResult.exception
//            }
            loadUserData(onSuccess)
//            onSuccess?.invoke()
            exception
        }
    }

    /**
     * 获取账户信息和当期用户
     */
    fun loadUserData(onSuccess: EmptyTask) {
        startBindLaunch {
            val tasks = arrayListOf(
                async { CurrentAccountInfoApi().suspendExecute() },
                async { CurrentUserApi().suspendExecute() }
            )
            val results = tasks.awaitAll()
            var exception: Exception? = null
            var accountValid = false
            var currentUserValid = false

            (results[0] as? SuspendResponse<AccountResponse>)?.let {
                exception = it.exception
                it.getResponse()?.body?.let { acocunt ->
                    DataCache.account = AccountBean().apply {
                        id = acocunt.phone
                        phone = acocunt.phone
                        xyBalance = acocunt.money
                    }
                    accountValid = true
                }
            }
            (results[1] as? SuspendResponse<UserResponse>)?.let {
                if (null == exception) {
                    exception = it.exception
                }
                it.getResponse()?.body?.let { user ->
                    DataCache.users.clear()
                    //TODO 没有头像信息
                    DataCache.users.add(UserBean.createUserInfo(user.subUserName, "")!!.apply {
                        userRecord = user
                        current = true
                    })
                    currentUserValid = true
                }
            }
            if (accountValid && currentUserValid) {
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