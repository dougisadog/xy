package com.meten.xyh.modules.usersetting.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.viewmodel.BaseViewModel

class SignatureViewModel : BaseViewModel() {

    var settingChange: SettingChange? = null

    val signature = MutableLiveData<String>()

    init {
        signature.value = DataCache.currentUser()?.introduction
    }


    fun saveSetting() {
        signature.value?.trim().let { signatrueText ->
            if (!signatrueText.isNullOrBlank()) {
                settingChange?.saveTask?.invoke(signatrueText)
            }
        }
    }

    class SettingChange(val user: SubUser?) {
        var title = ""
        var hint = ""
        var saveTask: ((String) -> Unit)? = null
        var type: UserSettingType? = null

        private fun getTargetUser(): SubUser? {
            return user ?: DataCache.users.first {
                it.current
            }.subUser
        }

        fun setUserSettingType(type: UserSettingType) {
            this.type = type
            val targetUser = user ?: DataCache.users.firstOrNull {
                it.current
            }?.subUser
            when (type) {
                UserSettingType.SIGNATURE -> {
                    title = "个性签名"
                    hint = "请输入个性签名"
                    saveTask = { input ->
                        getTargetUser()?.objective = input
                    }
                }
                UserSettingType.NICKNAME -> {
                    title = "修改昵称"
                    hint = "请输入昵称"
                    saveTask = { input ->
                        //todo 昵称保存
                        getTargetUser()?.objective = input
                    }
                }
            }
        }
    }
}