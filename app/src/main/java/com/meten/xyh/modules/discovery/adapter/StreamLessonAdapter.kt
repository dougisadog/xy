package com.meten.xyh.modules.discovery.adapter

import androidx.databinding.ObservableArrayList
import com.meten.xyh.BR
import com.meten.xyh.modules.discovery.bean.StreamLessonBean
import com.shuange.lesson.base.adapter.BaseListAdapter

class StreamLessonAdapter(layout: Int, data: ObservableArrayList<StreamLessonBean>?) :
    BaseListAdapter<StreamLessonBean>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: StreamLessonBean?) {
        helper?.binding?.setVariable(BR.streamLessonBean, item)
    }
}