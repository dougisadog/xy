package com.shuange.lesson.modules.topquality.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.topquality.bean.TopQualityBean
import com.shuange.lesson.BR

class TopQualityAdapter(layout: Int, data: ObservableArrayList<TopQualityBean>?) :
    BaseListAdapter<TopQualityBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: TopQualityBean?) {
        helper?.binding?.setVariable(BR.topQualityBean, item)
    }
}