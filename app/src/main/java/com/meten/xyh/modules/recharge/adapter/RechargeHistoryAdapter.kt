package com.meten.xyh.modules.recharge.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.modules.recharge.bean.RechargeHistoryBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class RechargeHistoryAdapter(layout: Int, data: ObservableArrayList<RechargeHistoryBean>?) :
    BaseListAdapter<RechargeHistoryBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: RechargeHistoryBean?) {
        helper?.binding?.setVariable(BR.rechargeHistoryBean, item)
    }
}