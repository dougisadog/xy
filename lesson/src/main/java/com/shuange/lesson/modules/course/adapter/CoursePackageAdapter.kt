package com.shuange.lesson.modules.course.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.course.bean.CoursePackageItem

class CoursePackageAdapter(layout: Int, data: ObservableArrayList<CoursePackageItem>?) :
    BaseListAdapter<CoursePackageItem>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: CoursePackageItem?) {
        helper?.binding?.setVariable(BR.courseInfoItem, item)
    }

}