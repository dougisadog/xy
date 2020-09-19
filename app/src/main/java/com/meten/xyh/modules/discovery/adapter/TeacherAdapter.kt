package com.meten.xyh.modules.discovery.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.modules.discovery.bean.TeacherBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class TeacherAdapter(layout: Int, data: ObservableArrayList<TeacherBean>?) :
    BaseListAdapter<TeacherBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: TeacherBean?) {
        helper?.binding?.setVariable(BR.teacherBean, item)
    }
}