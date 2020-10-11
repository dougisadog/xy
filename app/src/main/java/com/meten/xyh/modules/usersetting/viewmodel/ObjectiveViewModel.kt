package com.meten.xyh.modules.usersetting.viewmodel

import com.meten.xyh.modules.usersetting.bean.UserSettingBean
import com.meten.xyh.service.api.ObjectiveApi
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.service.api.base.suspendExecute

open class ObjectiveViewModel : BaseUserSettingViewModel() {
    override fun loadData() {
        startBindLaunch {
            val suspendResult = ObjectiveApi().suspendExecute()
            suspendResult.getResponse()?.body?.forEach {
                userSettingItems.add(UserSettingBean().apply {
                    title = it
                    isSelected = it == default
                })
            }
            suspendResult.exception
        }
    }

    override fun saveSetting(text: String, user: SubUser?) {
        user?.objective = text
        requestSave(user)
    }

}