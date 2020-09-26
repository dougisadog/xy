package com.shuange.lesson.modules.topquality.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.topquality.bean.CourseBean

class TopQualityAdapter(layout: Int, data: ObservableArrayList<CourseBean>?) :
    BaseListAdapter<CourseBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: CourseBean?) {
        helper?.binding?.setVariable(BR.topQualityCourseBean, item)
    }
}