package com.meten.xyh.modules.step.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.modules.step.bean.StepBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class StepAdapter(layout: Int, data: ObservableArrayList<StepBean>?) :
    BaseListAdapter<StepBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: StepBean?) {
        helper?.binding?.setVariable(BR.stepBean, item)
    }
}