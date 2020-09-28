package com.meten.xyh.modules.recharge.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meten.xyh.enumeration.PayType
import com.shuange.lesson.base.viewmodel.BaseViewModel

class RechargePayTypeViewModel : BaseViewModel() {

    val payType = MutableLiveData<PayType>()

}