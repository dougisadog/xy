package com.shuange.lesson.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.shuange.lesson.R
import com.shuange.lesson.modules.lesson.bean.Selection
import com.shuange.lesson.utils.extension.setSelectionImage
import kotlinx.android.synthetic.main.layout_pic_selection.view.*


class PicSelectionView : FrameLayout {

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

    fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_pic_selection, this)
    }

    private var selection: Selection? = null

    fun setPicAlpha(alpha: Float) {
        picIv.alpha = alpha
    }

    fun setSelection(selection: Selection) {
        this.selection = selection
        picIv.setSelectionImage(selection)
        resultIv.visibility = GONE
    }

    fun refreshState(): Boolean? {
        val selection = selection ?: return null
        resultIv.visibility = VISIBLE
        if (selection.bingo) {
            resultIv.setBackgroundResource(R.drawable.bg_pic_selection_right)
        } else {
            resultIv.setBackgroundResource(R.drawable.bg_pic_selection_wrong)
        }
        resultIv.background.mutate().alpha = (0.85 * 255).toInt()
        //TODO
        resultIv.setImageResource(R.drawable.icon_cancel)
        return selection.bingo
    }
}