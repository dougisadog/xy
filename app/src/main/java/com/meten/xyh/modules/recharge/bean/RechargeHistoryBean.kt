package com.meten.xyh.modules.recharge.bean

import com.meten.xyh.enumeration.PayStateType
import com.meten.xyh.enumeration.PayType
import com.shuange.lesson.base.PagerItem

class RechargeHistoryBean : PagerItem {
    var id: String = ""
    var xyValue = 0
    var dateTime = ""
    var payType: PayType? = null
    var rmbValue = 0
    var state: PayStateType? = null
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
}