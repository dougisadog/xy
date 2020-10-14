package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.service.api.DeleteRemindApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.SourceData

class UserInfoViewModel : BaseViewModel() {

    var user = MutableLiveData<SubUser>(DataCache.currentUser()?.subUser)

    var headerImg = MutableLiveData<SourceData>()

    fun loadData() {

    }

    /**
     * TODO
     * 缺少删除api
     */
    fun deletePhone() {
        DeleteRemindApi().execute {
            user.value?.phone = null
            DataCache.currentUser()?.subUser?.phone = null
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