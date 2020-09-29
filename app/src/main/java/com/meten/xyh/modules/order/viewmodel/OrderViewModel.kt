package com.meten.xyh.modules.order.viewmodel

import com.shuange.lesson.base.viewmodel.BaseViewModel

open class OrderViewModel : BaseViewModel() {

    val pager = arrayListOf("全部", "待支付", "已完成")


    fun loadData() {
    }

}