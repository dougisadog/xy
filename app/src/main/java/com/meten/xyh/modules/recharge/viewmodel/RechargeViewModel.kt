package com.meten.xyh.modules.recharge.viewmodel

import android.app.Activity
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.doug.paylib.util.*
import com.meten.xyh.enumeration.PayType
import com.meten.xyh.modules.recharge.bean.RechargeBean
import com.meten.xyh.service.api.RechargeApi
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

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

    fun createOrder(activity: Activity, price: Int, callback: PayCallback) {
        val type = payType.value ?: return
        startBindLaunch {
            val suspendResponse = RechargeApi(price, type).suspendExecute()
            suspendResponse.getResponse()?.body?.let {
                when (type) {
                    PayType.WECHAT -> {
                        val request = WepayRequest().apply { prepayid = it.prepayId }
                        WepayManager.getInstance().pay(request, callback)
                    }
                    PayType.ALIPAY -> {
                        val request = AlipayRequest().apply {
                            this.price = price.toDouble()
                            title = it.goodsName
                            body = "${it.goodsName} ${it.amount}"
                            tradeNo = it.tradeNo
                        }
                        AlipayManager.getInstance().pay(activity, request, callback)
                    }
                }
            }
            suspendResponse.exception
        }
    }
}