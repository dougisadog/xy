package com.shuange.lesson.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.shuange.lesson.R
import corelib.extension.roundInt
import corelib.util.DeviceUtils
import corelib.versionCheck.ApkInfo
import kotlinx.android.synthetic.main.layout_keyboard.view.*
import java.math.BigDecimal


class SimpleKeyboardView : RelativeLayout {

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


    private var inputText = ""

    var onTextChange: ((String) -> Unit)? = null

    fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_keyboard, this)
        val row1Data = arrayListOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
        val row2Data = arrayListOf("a", "s", "d", "f", "g", "h", "j", "k", "l")
        val row3Data = arrayListOf("z", "x", "c", "v", "b", "n", "m")

        val itemWidth = getItemWidth(row1Data)
        bindRowData(row1Data, row1Ll, itemWidth)
        bindRowData(row2Data, row2Ll, itemWidth)
        bindRowData(row3Data, row3Ll, itemWidth)
        reduceIv.setOnClickListener {
            reduce()
        }
    }

    val itemMargin = 5.0
    val sizeMargin = 16.0

    fun getItemWidth(data: ArrayList<*>): Int {
        val itemWidth = 1.0 * (ApkInfo.width - (data.size - 1) * DeviceUtils.toPx(
            context,
            itemMargin
        ) - DeviceUtils.toPx(context, sizeMargin) * 2) / data.size
        return BigDecimal(itemWidth).roundInt()
    }

    private fun bindRowData(data: ArrayList<String>, container: LinearLayout, width: Int) {
        container.removeAllViews()

        val layoutParams = LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT)
        val radius = DeviceUtils.toPx(context, itemMargin / 2)
        layoutParams.setMargins(radius, 0, radius, 0)
        data.forEach {
            val tv = TextView(context)
            tv.text = it
            tv.textSize = 18f
            tv.setTextColor(ContextCompat.getColor(context, R.color.hex_464646))
            tv.gravity = Gravity.CENTER
            tv.layoutParams = layoutParams
            tv.setBackgroundResource(R.drawable.bg_text_keyboard_item)
            tv.setOnClickListener {
                add(tv.text.toString())
            }
            container.addView(tv)
        }
    }

    fun add(text: String) {
        inputText += text
        onTextChange?.invoke(inputText)
    }

    fun reduce() {
        if (inputText.isEmpty()) return
        inputText = inputText.substring(0, inputText.length - 1)
        onTextChange?.invoke(inputText)
    }

}