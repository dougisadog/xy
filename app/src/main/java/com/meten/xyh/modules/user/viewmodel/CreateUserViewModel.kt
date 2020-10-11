package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.service.api.CurrentUserApi
import com.meten.xyh.service.api.SubUserEditApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

class CreateUserViewModel : BaseViewModel() {

    val user = MutableLiveData<SubUser>()

    fun loadData() {

    }

    fun save(success:EmptyTask) {
        val user = DataCache.newSubUser
        if (null == user) {
            DataCache.newSubUser = SubUser()
            save(success)
            return
        }
        startBindLaunch(showLoading = true) {
            var exception: Exception? = null
            val result = SubUserEditApi(user).suspendExecute()
            exception = result.exception
            if (null != exception) {
                CurrentUserApi().suspendExecute().let {
                    exception = it.exception
                }
            }
            if (null == exception) {
                DataCache.newSubUser = null
                success?.invoke()
            }
            exception
        }
    }
}