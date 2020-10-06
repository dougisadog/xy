package com.meten.xyh.service.response.bean

import com.shuange.lesson.service.response.bean.BaseBean

class Order : BaseBean() {
    var amount: Int = 0
    var goodsId: Int = 0
    var goodsImageUrl: String = ""
    var goodsName: String = ""
    var login: String = ""
    var no: String = ""
    var paidStatus: String = ""
    var phone: String = ""
    var prepayId: String = ""
    var refundStatus: String = ""
    var tradeNo: String = ""
    var transactionId: String = ""
    var userId: Int = 0
}
