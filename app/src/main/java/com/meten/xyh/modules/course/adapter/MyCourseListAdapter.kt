package com.meten.xyh.modules.course.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.modules.course.bean.MyCourseBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class MyCourseListAdapter(layout: Int, data: ObservableArrayList<MyCourseBean>?) :
    BaseListAdapter<MyCourseBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: MyCourseBean?) {
        helper?.binding?.setVariable(BR.myCourseBean, item)
    }
}