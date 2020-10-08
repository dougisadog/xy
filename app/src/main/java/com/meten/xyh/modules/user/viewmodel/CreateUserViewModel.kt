package com.meten.xyh.modules.user.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.bean.ActionItem
import com.meten.xyh.service.api.CurrentUserApi
import com.meten.xyh.service.api.SubUserEditApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

class CreateUserViewModel : BaseViewModel() {

    val actionItems = ObservableArrayList<ActionItem>()

    val user: SubUser? = SubUser()

    fun loadData() {

    }

    fun save() {
        val user = DataCache.newSubUser
        if (null == user) {
            DataCache.newSubUser = SubUser()
            save()
            return
        }
        startBindLaunch {
            var exception: Exception? = null
            val result = SubUserEditApi(user).suspendExecute()
            exception = result.exception

            CurrentUserApi().suspendExecute().let {
                if (null == exception) {
                    exception = it.exception
                }
            }
            DataCache.newSubUser = null
            exception

        }
    }
}