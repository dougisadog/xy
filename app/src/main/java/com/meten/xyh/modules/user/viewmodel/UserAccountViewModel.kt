package com.meten.xyh.modules.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.user.bean.UserBean
import com.meten.xyh.service.api.ExchangeCodeApi
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

class UserAccountViewModel : BaseViewModel() {

    val subUser = MutableLiveData<UserBean>(DataCache.currentUser())

    val account = LessonDataCache.account

    var giftSuccess = MutableLiveData<String>()

    fun verifyGiftCode(giftCode: String) {
        startBindLaunch {
            val suspendResult = ExchangeCodeApi(giftCode).suspendExecute()
            suspendResult.getResponse()?.body?.let {
                giftSuccess.value = it.lessonPackageName
            }
            suspendResult.exception
        }
    }


}