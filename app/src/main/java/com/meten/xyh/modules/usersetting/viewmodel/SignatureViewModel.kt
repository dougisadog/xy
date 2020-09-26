package com.meten.xyh.modules.usersetting.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.shuange.lesson.base.viewmodel.BaseViewModel

class SignatureViewModel : BaseViewModel() {

    val signature = MutableLiveData<String>()

    init {
        signature.value = DataCache.currentUser()?.introduction
    }


    fun saveSetting() {
        signature.value?.let { signatrueText ->
            DataCache.users.first {
                it.current
            }.let {
                it.subUser?.objective = signatrueText.trim()
            }
        }

    }
}