package com.meten.xyh.modules.discovery.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.modules.discovery.bean.TopQualityBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class TopQualityAdapter(layout: Int, data: ObservableArrayList<TopQualityBean>?) :
    BaseListAdapter<TopQualityBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: TopQualityBean?) {
        helper?.binding?.setVariable(BR.topQualityBean, item)
    }
}