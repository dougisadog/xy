package com.shuange.lesson.modules.course.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.course.bean.CourseInfoItem

class CourseInfoAdapter(layout: Int, data: ObservableArrayList<CourseInfoItem>?) :
    BaseListAdapter<CourseInfoItem>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: CourseInfoItem?) {
        helper?.binding?.setVariable(BR.courseInfoItem, item)
    }

}