package com.meten.xyh.modules.recharge.bean

class RechargeBean {

    var xyValue: Int = 0
    var rmbValue = 0

    var isSelected = false

    val xyText: String
        get() {
            return "${xyValue}希氧币"
        }

    val rmbText: String
        get() {
            return "${rmbValue}元"
        }
}