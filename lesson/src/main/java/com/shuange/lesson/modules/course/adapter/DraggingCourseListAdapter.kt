package com.shuange.lesson.modules.course.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.course.bean.DraggingCourseBean

class DraggingCourseListAdapter(layout: Int, data: ObservableArrayList<DraggingCourseBean>?) :
    BaseListAdapter<DraggingCourseBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: DraggingCourseBean?) {
        helper?.binding?.setVariable(BR.draggingCourseBean, item)
    }
}