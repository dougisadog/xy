package com.shuange.lesson.modules.course.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.course.bean.MyCourseBean

class MyCourseListAdapter(layout: Int, data: ObservableArrayList<MyCourseBean>?) :
    BaseListAdapter<MyCourseBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: MyCourseBean?) {
        helper?.binding?.setVariable(BR.myCourseBean, item)
    }
}