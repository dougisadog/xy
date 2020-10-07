package com.shuange.lesson.modules.course.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.databinding.LayoutCourseItemBinding
import com.shuange.lesson.modules.course.bean.CourseItem
import com.shuange.lesson.utils.BusinessUtil
import corelib.util.ContextManager
import corelib.util.DeviceUtils

class CourseAdapter(layout: Int, data: ObservableArrayList<CourseItem>?) :
    BaseListAdapter<CourseItem>(layout, data) {
    override fun convert(helper: ListViewHolder?, item: CourseItem?) {
        helper?.binding?.setVariable(BR.courseItem, item)
        (helper?.binding as? LayoutCourseItemBinding)?.let {
            it.startsContainer.removeAllViews()
            BusinessUtil.refreshResult(
                target = it.startsContainer,
                stars = item?.progress ?: 0,
                width = DeviceUtils.toPx(ContextManager.getContext(), 12.0)
            )
        }
    }

}