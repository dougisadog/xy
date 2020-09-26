package com.meten.xyh.base.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import java.util.*

open class VerifyMessageViewModel : BaseViewModel() {

    val maxTime = 60

    var verifyCode = MutableLiveData<String>()

    var remainTime = MutableLiveData<Int>()

    fun sendVerifyCode() {
        var remain = maxTime
        remainTime.value = remain
        sendMessage()

        Timer().schedule(object : TimerTask() {
            override fun run() {
                remain--
                if (remain < 0) {
                    cancel()
                }
                remainTime.postValue(remain)
            }
        }, 1000L, 1000L)
    }

    //TODO SEND MESSAGE
    fun sendMessage() {

    }
}