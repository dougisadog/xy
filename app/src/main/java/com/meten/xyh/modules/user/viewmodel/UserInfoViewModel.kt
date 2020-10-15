package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.service.api.CurrentUserApi
import com.meten.xyh.service.api.DeleteRemindApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.SourceData
import com.shuange.lesson.service.api.base.suspendExecute

class UserInfoViewModel : BaseViewModel() {

    var user = MutableLiveData<SubUser>(DataCache.currentUser()?.subUser)

    var headerImg = MutableLiveData<SourceData>()

    fun loadData() {

    }

    fun loadCurrentUser() {
        startBindLaunch {
            val suspendResult =  CurrentUserApi().suspendExecute()
            suspendResult.getResponse()?.body?.let {
                user.value = DataCache.currentUser()?.subUser
            }
            suspendResult.exception
        }
    }
    fun deletePhone() {
        DeleteRemindApi().execute {
            user.value?.phone = null
            DataCache.currentUser()?.subUser?.let {
                it.phone = null
                user.value = it
            }

        }
//        user.value?.let {
//            if (-1L == it.id) {
//                it.phone = null
//                startBindLaunch(showLoading = true) {
//                    var exception: Exception? = null
//                    val result = SubUserEditApi(it).suspendExecute()
//                    exception
//                }
//            }
//        }
    }
}