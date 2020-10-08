package com.meten.xyh.modules.discovery.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.modules.course.bean.StreamLessonBean

class StreamLessonAdapter(layout: Int, data: ObservableArrayList<StreamLessonBean>?) :
    BaseListAdapter<StreamLessonBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: StreamLessonBean?) {
        helper?.binding?.setVariable(BR.streamLessonBean, item)
    }
}