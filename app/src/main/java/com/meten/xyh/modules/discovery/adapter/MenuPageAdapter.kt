package com.meten.xyh.modules.discovery.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meten.xyh.modules.discovery.bean.MenuItem
import com.meten.xyh.view.CustomGridLayout
import com.meten.xyh.view.MenuItemView

class MenuPageAdapter(
    private val context: Context,
    private val items: MutableList<MutableList<MenuItem>>
) :
    RecyclerView.Adapter<MenuPageAdapter.MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(CustomGridLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            columnCount = 4
            rowCount = 2
        })
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        (holder.itemView as CustomGridLayout).initItems(
            itemCreator = { MenuItemView(context) },
            data = items[position],
            binder = { t, d ->
                t.setItem(d)
            }
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}