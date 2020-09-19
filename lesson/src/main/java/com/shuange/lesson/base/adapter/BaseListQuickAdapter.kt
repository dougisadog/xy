package com.shuange.lesson.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shuange.lesson.R

open class BaseListQuickAdapter<T>(
    layout: Int,
    data: MutableList<T>,
    private var brId: Int
) : BaseQuickAdapter<T, BaseListQuickAdapter.ListViewHolder>(layout, data) {

    override fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        return view
    }

    class ListViewHolder(view: View) : BaseViewHolder(view) {
        val binding: ViewDataBinding
            get() = itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
    }

    override fun convert(helper: ListViewHolder?, item: T) {
        helper?.let {
            with(it) {
                binding.setVariable(brId, item)
                binding.executePendingBindings()
            }
        }
    }
}