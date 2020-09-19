package com.meten.xyh.modules.discovery.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class BaseItemAdapter(layout: Int, data: ObservableArrayList<BaseItemBean>?) :
    BaseListAdapter<BaseItemBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: BaseItemBean?) {
        helper?.binding?.setVariable(BR.baseItemBean, item)
    }
}