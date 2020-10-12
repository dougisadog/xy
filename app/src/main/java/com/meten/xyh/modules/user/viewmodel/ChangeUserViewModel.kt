package com.meten.xyh.modules.user.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.user.bean.UserBean
import com.meten.xyh.service.api.CurrentUserApi
import com.meten.xyh.service.api.SubUserSetDefaultApi
import com.meten.xyh.service.api.SubUsersApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.EmptyTask
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

    fun saveUser(subUser: SubUser, onSuccess: EmptyTask) {
        startBindLaunch {
            var exception: Exception? = null
            val result = SubUserSetDefaultApi(subUser.id.toString()).suspendExecute()
            exception = result.exception
            CurrentUserApi().suspendExecute().let {
                if (null == exception) {
                    exception = it.exception
                }
                if (null != it.getResponse()?.body) {
                    onSuccess?.invoke()
                }
            }
            exception
        }
    }
}