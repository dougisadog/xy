package com.meten.xyh.modules.order.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.enumeration.OrderState
import com.meten.xyh.modules.order.bean.OrderBean
import com.meten.xyh.service.api.TeachersApi
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

class OrderListViewModel : BaseViewModel() {

    var state: OrderState? = null

    val orders = ObservableArrayList<OrderBean>()

    fun loadData() {
        loadOrders("0")
    }

    fun loadOrders(
        startId: String = orders.lastOrNull()?.getItemId() ?: "0",
        page: Int = 50,
        onFinished: EmptyTask = null
    ) {
        startBindLaunch(onFinish = onFinished) {
            val suspendResult = TeachersApi().suspendExecute()
            suspendResult.getResponse()?.body?.forEach {
//                teachers.add(TeacherBean().apply {
//                    setTeacher(it)
//                })
            }
            suspendResult.exception
        }
        testData()
    }

    fun testData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 6) {
            orders.add(OrderBean().apply {
                title = "XXXXXXXXXXXXXXXX课程.....$i"
                state = if (i % 2 == 0) OrderState.PENDING else OrderState.FINISHED
                count = i
                price = i * i
                orderNo = "20200714213333333333333$i"
                orderDate = "2020-07-10   18:29:1$i"
                payDate = "2020-07-10   19:29:1$i"
                pageText = "订单-已完成"
                expressNo = "1111111111111111111$i"

            })
        }
    }
}