package com.shuange.lesson.modules.course.adapter

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.BR
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.databinding.LayoutCourseItemBinding
import com.shuange.lesson.modules.course.bean.CourseLessonItem
import com.shuange.lesson.utils.BusinessUtil
import corelib.util.ContextManager
import corelib.util.DeviceUtils

class CourseLessonAdapter(layout: Int, data: ObservableArrayList<CourseLessonItem>?) :
    BaseListAdapter<CourseLessonItem>(layout, data) {
    override fun convert(helper: ListViewHolder?, lessonItem: CourseLessonItem?) {
        helper?.binding?.setVariable(BR.courseItem, lessonItem)
        (helper?.binding as? LayoutCourseItemBinding)?.let {
            it.startsContainer.removeAllViews()
            BusinessUtil.refreshResult(
                target = it.startsContainer,
                stars = lessonItem?.stars ?: 0,
                width = DeviceUtils.toPx(ContextManager.getContext(), 12.0)
            )
        }
    }

}