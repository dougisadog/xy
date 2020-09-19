package com.shuange.lesson.modules.topquality.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.topquality.bean.TopQualityCourseBean
import com.shuange.lesson.BR

class TopQualityAdapter(layout: Int, data: ObservableArrayList<TopQualityCourseBean>?) :
    BaseListAdapter<TopQualityCourseBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: TopQualityCourseBean?) {
        helper?.binding?.setVariable(BR.topQualityCourseBean, item)
    }
}