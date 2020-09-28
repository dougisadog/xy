package com.meten.xyh.modules.recharge.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.modules.recharge.bean.RechargeBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class RechargeAdapter(data: ObservableArrayList<RechargeBean>?) :
    BaseListAdapter<RechargeBean>(R.layout.layout_recharge_item, data) {
    override fun convert(helper: ListViewHolder?, item: RechargeBean?) {
        helper?.binding?.setVariable(BR.rechargeBean, item)
    }
}