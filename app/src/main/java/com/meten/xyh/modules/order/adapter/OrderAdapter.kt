package com.meten.xyh.modules.order.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.modules.order.bean.OrderBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class OrderAdapter(data: ObservableArrayList<OrderBean>?) :
    BaseListAdapter<OrderBean>(R.layout.layout_order_item, data) {
    override fun convert(helper: ListViewHolder?, item: OrderBean?) {
        helper?.binding?.setVariable(BR.orderBean, item)
    }
}