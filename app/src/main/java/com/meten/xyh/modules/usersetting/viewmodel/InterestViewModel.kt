package com.meten.xyh.modules.usersetting.viewmodel

import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.usersetting.bean.UserSettingBean
import com.meten.xyh.service.api.InterestApi
import com.shuange.lesson.service.api.base.suspendExecute

open class InterestViewModel : BaseUserSettingViewModel() {
    override fun loadData() {
        startBindLaunch {
            val suspendResult = InterestApi().suspendExecute()
            suspendResult.getResponse()?.body?.forEach {
                userSettingItems.add(UserSettingBean().apply { title = it })
            }
            suspendResult.exception
        }
    }

    override fun saveSetting(text: String) {
        DataCache.users.first {
            it.current
        }.let {
            it.subUser?.interest = text
        }
    }

}