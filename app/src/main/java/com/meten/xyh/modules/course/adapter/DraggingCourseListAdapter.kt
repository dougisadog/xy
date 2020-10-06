package com.meten.xyh.modules.course.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.modules.course.bean.DraggingCourseBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class DraggingCourseListAdapter(layout: Int, data: ObservableArrayList<DraggingCourseBean>?) :
    BaseListAdapter<DraggingCourseBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: DraggingCourseBean?) {
        helper?.binding?.setVariable(BR.draggingCourseBean, item)
    }
}