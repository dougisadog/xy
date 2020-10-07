package com.meten.xyh.modules.recharge.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.meten.xyh.enumeration.PayType
import com.meten.xyh.modules.recharge.bean.RechargeBean
import com.shuange.lesson.base.viewmodel.BaseViewModel

class RechargeViewModel : BaseViewModel() {

    val rechargeItems = ObservableArrayList<RechargeBean>()

    val balance = MutableLiveData<Double>(0.0)


    val payType = MutableLiveData<PayType>(PayType.WECHAT)

    init {
        rechargeItems.add(RechargeBean().apply {
            rmbValue = 60
            xyValue = 600
            isSelected = true
        })
        rechargeItems.add(RechargeBean().apply {
            rmbValue = 80
            xyValue = 800
            isSelected = false
        })
        rechargeItems.add(RechargeBean().apply {
            rmbValue = 120
            xyValue = 1200
            isSelected = false
        })
        rechargeItems.add(RechargeBean().apply {
            rmbValue = 600
            xyValue = 6000
            isSelected = false
        })
    }

    fun createOrder() {

    }
}