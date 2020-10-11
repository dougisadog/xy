package com.meten.xyh.modules.usersetting.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.meten.xyh.modules.usersetting.bean.UserSettingBean
import com.meten.xyh.service.api.SubUserEditApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

abstract class BaseUserSettingViewModel : BaseViewModel() {

    var default: String? = null

    val type = 0

    val userSettingItems = ObservableArrayList<UserSettingBean>()

    val settingUpdated = MutableLiveData<Boolean>()

    abstract fun loadData()

    abstract fun saveSetting(text: String, user: SubUser?)

    fun requestSave(user: SubUser?) {
        user?.let {
            if (-1L == it.id) {
                startBindLaunch(showLoading = true) {
                    var exception: Exception? = null
                    val result = SubUserEditApi(user).suspendExecute()
                    exception = result.exception
                    if (null == exception) {
                        settingUpdated.value = true
                    }
                    exception
                }
            } else {
                settingUpdated.value = true
            }
        }
    }

}