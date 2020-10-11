package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.service.api.ChangeRemindApi
import com.meten.xyh.service.api.SubUserEditApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.SourceData
import com.shuange.lesson.service.api.base.suspendExecute

class UserInfoViewModel : BaseViewModel() {

    var user = MutableLiveData<SubUser>(DataCache.currentUser()?.subUser)

    var headerImg = MutableLiveData<SourceData>()

    fun loadData() {

    }

    fun deletePhone() {
        DataCache.currentUser()?.subUser?.let {
            it.phone = ""
            startBindLaunch(showLoading = true) {
                var exception: Exception? = null
                val suspendResult = ChangeRemindApi(it.phone!!).suspendExecute()
                exception = suspendResult.exception
                if (null != exception) {
                    val result = SubUserEditApi(it).suspendExecute()
                    exception = result.exception
                }
                exception
            }
        }
    }
}