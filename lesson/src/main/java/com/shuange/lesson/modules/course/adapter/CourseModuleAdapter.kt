package com.shuange.lesson.modules.course.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.databinding.LayoutCourseItemBinding
import com.shuange.lesson.modules.course.bean.CourseModuleItem
import com.shuange.lesson.utils.BusinessUtil
import corelib.util.ContextManager
import corelib.util.DeviceUtils


class CourseModuleAdapter : BaseMultiItemQuickAdapter<CourseModuleItem, BaseListAdapter.ListViewHolder> {

    var data: ObservableArrayList<CourseModuleItem>

    constructor (data: ObservableArrayList<CourseModuleItem>) : super(data) {
        this.data = data
        data.addOnListChangedCallback(object :
            ObservableList.OnListChangedCallback<ObservableList<String>>() {
            override fun onChanged(sender: ObservableList<String>?) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(
                sender: ObservableList<String>?,
                positionStart: Int,
                itemCount: Int
            ) {
                if (sender!!.isEmpty()) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRangeRemoved(positionStart, itemCount)
                }
            }

            override fun onItemRangeMoved(
                sender: ObservableList<String>?,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeInserted(
                sender: ObservableList<String>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(
                sender: ObservableList<String>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeChanged(positionStart, itemCount)
            }

        })
        addItemType(CourseModuleItem.COURSE_TITLE, R.layout.layout_course_title)
        addItemType(CourseModuleItem.COURSE_ITEM, R.layout.layout_course_item)
    }


    override fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            mLayoutInflater,
            layoutResId,
            parent,
            false
        )
            ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        return view
    }

    override fun convert(helper: BaseListAdapter.ListViewHolder?, lessonItem: CourseModuleItem?) {
        helper?.binding?.setVariable(BR.courseItem, lessonItem)
        (helper?.binding as? LayoutCourseItemBinding)?.let {
            BusinessUtil.refreshResult(
                target = it.startsContainer,
                stars = lessonItem?.stars ?: 0,
                width = DeviceUtils.toPx(ContextManager.getContext(), 12.0)
            )
        }
    }

}