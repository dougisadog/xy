package com.meten.xyh.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout
import corelib.util.DeviceUtils


class CustomGridLayout : GridLayout {

    private var leftMargin: Int = 0
    private var topMargin: Int = 0
    private var rightMargin: Int = 0
    private var bottomMargin: Int = 0


    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initView(attrs)
    }

    fun setItemMargins(left: Int, top: Int, right: Int, bottom: Int) {
        leftMargin = left
        topMargin = top
        rightMargin = right
        bottomMargin = bottom
    }

    fun initView(attrs: AttributeSet? = null) {
    }

    fun <T : View, D> initItems(
        itemCreator: () -> T,
        data: MutableList<D>,
        binder: (T, D) -> Unit
    ) {
        removeAllViews()
        if (data.size > columnCount * rowCount) {
            rowCount = data.size / columnCount + 1
        }

        data.forEachIndexed { index, d ->
            val params = LayoutParams()
            params.width = 0
            params.height = 0
            val columnStart = index % columnCount
            //match parent by weight
            params.columnSpec = spec(columnStart, 1, 1f)
            //from start,display by width
//            params.columnSpec = spec(columnStart, 1)
//            params.width = 50
            params.height = DeviceUtils.toPx(context, 90)
            val rowStart = index / columnCount
            params.rowSpec = spec(rowStart, 1, 1f)
            params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
            val item = itemCreator.invoke()
            binder.invoke(item, d)
            addView(item, params)
        }

    }


}