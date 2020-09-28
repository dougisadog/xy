package com.meten.xyh.modules.usersetting.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.enumeration.SignatureType
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

    class SettingChange {
        var title = ""
        var hint = ""
        var saveTask: ((String) -> Unit)? = null
        var type: SignatureType? = null

        fun setSignatureType(type: SignatureType) {
            this.type = type
            when (type) {
                SignatureType.SIGNATURE -> {
                    title = "个性签名"
                    hint = "请输入个性签名"
                    saveTask = { input ->
                        DataCache.users.first {
                            it.current
                        }.let {
                            it.subUser?.objective = input
                        }
                    }
                }
                SignatureType.NICKNAME -> {
                    title = "修改昵称"
                    hint = "请输入昵称"
                    saveTask = { input ->
                        DataCache.users.first {
                            it.current
                        }.let {
                            //todo 昵称保存
                            it.subUser?.objective = input
                        }
                    }
                }
            }
        }
    }
}