package com.shuange.lesson.base.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.base.BaseItemBean

class BaseItemAdapter(layout: Int, data: ObservableArrayList<BaseItemBean>?) :
    BaseListAdapter<BaseItemBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: BaseItemBean?) {
        helper?.binding?.setVariable(BR.baseItemBean, item)
    }
}