package com.meten.xyh.view

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.meten.xyh.R
import com.meten.xyh.modules.discovery.bean.MenuItem
import kotlinx.android.synthetic.main.layout_menu_item.view.*

class MenuItemView : RelativeLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    var menuItem: MenuItem? = null


    fun init() {
        LayoutInflater.from(context).inflate(R.layout.layout_menu_item, this)
    }

    fun setItem(menuItem: MenuItem) {
        this.menuItem = menuItem
        val image = BitmapFactory.decodeResource(context.resources, menuItem.imageSource)
        Glide.with(this).load(image).into(menuTitleIv)
        menuTitleTv.text = menuItem.title
        setOnClickListener {
            menuItem.onClick?.invoke()
        }
    }
}