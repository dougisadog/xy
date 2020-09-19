package com.meten.xyh.base.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.bean.ActionItem
import com.shuange.lesson.base.adapter.BaseListAdapter

class ActionAdapter(data: ObservableArrayList<ActionItem>?) :
    BaseListAdapter<ActionItem>(R.layout.layout_action_item, data) {
    override fun convert(helper: ListViewHolder?, item: ActionItem?) {
        helper?.binding?.setVariable(BR.actionItem, item)
    }
}