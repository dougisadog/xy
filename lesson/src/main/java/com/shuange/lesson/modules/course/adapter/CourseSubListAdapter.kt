package com.shuange.lesson.modules.course.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.course.bean.CourseSubItem

class CourseSubListAdapter(data: ObservableArrayList<CourseSubItem>?) :
    BaseListAdapter<CourseSubItem>(R.layout.layout_course_sub_item, data) {
    override fun convert(helper: ListViewHolder?, item: CourseSubItem?) {
        helper?.binding?.setVariable(BR.courseSubItem, item)
    }
}