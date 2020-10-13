package com.meten.xyh.base.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel
import java.util.*

abstract class VerifyMessageViewModel : BaseViewModel() {

    val maxTime = 60

    var verifyCode = MutableLiveData<String>()

    var remainTime = MutableLiveData<Int>()

    fun sendVerifyCode(onSuccess:EmptyTask = null) {
        sendMessage {
            var remain = maxTime
            remainTime.value = remain
            onSuccess?.invoke()
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
    }

    abstract fun sendMessage(onSuccess:EmptyTask)
}