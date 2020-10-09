package com.meten.xyh.modules.usersetting.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel

class SignatureViewModel : BaseViewModel() {

    var settingChange =  MutableLiveData<SettingChange>()

    val signature = MutableLiveData<String>()

    fun saveSetting() {
        signature.value?.trim().let { signatrueText ->
            if (!signatrueText.isNullOrBlank()) {
                settingChange.value?.saveTask?.invoke(signatrueText)
            }
        }
    }

    class SettingChange(val user: SubUser?) {
        var title = ""
        var hint = ""
        var value = ""
        var saveTask: ((String) -> Unit)? = null
        var type: UserSettingType? = null

        private fun getTargetUser(): SubUser? {
            return user ?: DataCache.users.first {
                it.current
            }.subUser
        }

        fun setUserSettingType(type: UserSettingType) {
            this.type = type
            when (type) {
                UserSettingType.SIGNATURE -> {
                    title = "个性签名"
                    hint = "请输入个性签名"
                    saveTask = { input ->
                        //未知
                        getTargetUser()?.objective = input
                    }
                }
                UserSettingType.NICKNAME -> {
                    title = "修改昵称"
                    hint = "请输入昵称"
                    saveTask = { input ->
                        getTargetUser()?.name = input
                    }
                }
                else -> {

                }
            }
        }
    }
}