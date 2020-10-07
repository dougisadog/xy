package com.meten.xyh.modules.recharge.bean

import com.meten.xyh.enumeration.OrderState
import com.meten.xyh.enumeration.PayType
import com.meten.xyh.service.response.bean.Recharge
import com.meten.xyh.utils.TimeUtil
import com.shuange.lesson.base.PagerItem

class RechargeHistoryBean : PagerItem {
    var id: String = ""
    var xyValue = 0
    var dateTime = ""
    var payType: PayType? = null
    var rmbValue = 0
    var state: OrderState? = null
    var orderNo: String = ""
    override fun getItemId(): String {
        return id
    }

    val date: String
        get() {
            return dateTime
        }

    val xyText: String
        get() {
            return "${xyValue}希氧币"
        }

    val contentText: String
        get() {
            return "充值${xyValue}希氧币成功"
        }

    fun setRecharge(recharge: Recharge) {
        id = recharge.id.toString()
        xyValue = recharge.money
        //TODO 缺少支付方式
//        payType = PayType.buildByText(recharge.)
        rmbValue = recharge.amount
        state = OrderState.buildByText(recharge.paidStatus)
        orderNo = recharge.no
        dateTime =
            if (state == OrderState.PAID) TimeUtil.parseTime(recharge.lastModifiedDate) else ""
    }
}