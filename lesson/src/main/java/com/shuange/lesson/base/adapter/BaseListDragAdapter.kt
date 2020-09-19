package com.shuange.lesson.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shuange.lesson.R

/**
 * @Description: base drag class
 */
open class BaseListDragAdapter<T>(
    layoutId: Int,
    data: MutableList<T>?,
    private var brId: Int
) : BaseItemDraggableAdapter<T, BaseListDragAdapter.DragViewHolder>(layoutId, data) {


    override fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        return view
    }

    override fun convert(helper: DragViewHolder, item: T) {
        with(helper) {
            binding.setVariable(brId, item)
            binding.executePendingBindings()
        }
    }

    class DragViewHolder(view: View) : BaseViewHolder(view) {
        val binding: ViewDataBinding
            get() = itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
    }
}