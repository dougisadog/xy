package com.meten.xyh.modules.order.bean

import com.meten.xyh.enumeration.OrderState
import com.meten.xyh.service.response.bean.Order
import com.meten.xyh.utils.TimeUtil
import com.shuange.lesson.base.PagerItem

class OrderBean : PagerItem {

    var orderId: String = ""
    var state: OrderState? = null
    var title = ""
    var count = 0
    var price = 0
    var orderNo = ""
    var orderDate = ""
    var payDate = ""
    var pageText = "" //页面？
    var expressNo = "" //快递单号

    val countText: String
        get() {
            return "数量X${count}"
        }

    val priceText: String
        get() {
            return "应付金额：${price}元"
        }

    override fun getItemId(): String {
        return orderId
    }

    fun setOrder(order: Order) {
        orderId = order.id.toString()
        state = OrderState.buildByText(order.paidStatus)

        title = order.goodsName
        count = 1
        price = order.amount
        orderNo = order.no
        orderDate = TimeUtil.parseTime(order.createdDate)
        payDate = if (state == OrderState.PAID) TimeUtil.parseTime(order.lastModifiedDate) else ""
        pageText = "订单-${state?.text}"
        //TODO 快递单号
        expressNo = ""
    }
}