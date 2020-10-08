package com.meten.xyh.modules.user.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.login.AccountBean
import com.meten.xyh.modules.user.bean.UserBean
import com.meten.xyh.service.api.CurrentUserApi
import com.meten.xyh.service.api.SubUserSetDefaultApi
import com.meten.xyh.service.api.SubUsersApi
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

class ChangeUserViewModel : BaseViewModel() {

    val users = ObservableArrayList<UserBean>()

    init {
//        testData()
        getUser()
    }

    fun getUser() {
        startBindLaunch {
            val suspendResult = SubUsersApi().suspendExecute()
            suspendResult.getResponse()?.body?.let {
                users.clear()
                users.addAll(DataCache.users)
            }
            suspendResult.exception
        }
    }

    fun testData() {
        DataCache.users.clear()
        DataCache.account = AccountBean().apply { id = "test" }
        UserBean.createUserInfo(
            "你的名字是什么…",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        )
            ?.apply { current = true }?.let { DataCache.users.add(it) }
        UserBean.createUserInfo(
            "我的第二个账号",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        )
            ?.let { DataCache.users.add(it) }
    }

    fun saveUser() {
        DataCache.users = users.toMutableList()
        DataCache.currentUser()?.userId?.let {
            startBindLaunch {
                var exception: Exception? = null
                val result = SubUserSetDefaultApi(it).suspendExecute()
                exception = result.exception
                CurrentUserApi().suspendExecute().let {
                    if (null == exception) {
                        exception = it.exception
                    }
                }
                exception
            }
        }
    }
}