package com.meten.xyh.service.response.bean

import com.shuange.lesson.service.response.bean.BaseBean

class Recharge : BaseBean() {
    var amount: Int = 0
    var login: String = ""
    var money: Int = 0
    var no: String = ""
    var paidStatus: String = ""
    var phone: String = ""
    var prepayId: String = ""
    var refundStatus: String = ""
    var tradeNo: String = ""
    var transactionId: String = ""
    var userId: Long = 0
}