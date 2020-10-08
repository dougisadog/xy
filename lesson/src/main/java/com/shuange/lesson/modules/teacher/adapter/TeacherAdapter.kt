package com.shuange.lesson.modules.teacher.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.teacher.bean.TeacherBean

class TeacherAdapter(layout: Int, data: ObservableArrayList<TeacherBean>?) :
    BaseListAdapter<TeacherBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: TeacherBean?) {
        helper?.binding?.setVariable(BR.teacherBean, item)
    }
}