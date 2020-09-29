package com.meten.xyh.modules.usersetting.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.modules.usersetting.bean.InterestBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class InterestAdapter(layout: Int, data: ObservableArrayList<InterestBean>?) :
    BaseListAdapter<InterestBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: InterestBean?) {
        helper?.binding?.setVariable(BR.interestBean, item)
    }
}