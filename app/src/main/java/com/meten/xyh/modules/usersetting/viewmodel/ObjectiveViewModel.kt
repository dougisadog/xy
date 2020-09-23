package com.meten.xyh.modules.usersetting.viewmodel

import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.usersetting.bean.UserSettingBean
import com.meten.xyh.service.api.ObjectiveApi
import com.shuange.lesson.service.api.base.suspendExecute

open class ObjectiveViewModel : BaseUserSettingViewModel() {
    override fun loadData() {
        startBindLaunch {
            val suspendResult = ObjectiveApi().suspendExecute()
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
            it.subUser?.objective = text
        }
    }

}