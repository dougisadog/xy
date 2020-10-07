package com.shuange.lesson.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.shuange.lesson.R
import corelib.util.ContextManager
import corelib.util.DeviceUtils
import corelib.util.Log


class Keyboard8View : LinearLayout {

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

    companion object {
        const val TAG = "Keyboard8View"

        const val MAX_ERROR = 3

        const val MAX = 8
    }

    private val source = arrayListOf<String>()

    //第几个字母
    var process = 0

    //错误数
    var errorProcess = 0

    private var fullWords = ""

    //当前结果
    private var inputText = ""

    //当前结果callback
    var listner: InputListener? = null

    //当前单词的字母
    private val currentCharPool: ArrayList<Char> = arrayListOf()

    //26字母
    private val fullCharPool: ArrayList<Char> = arrayListOf()
        get() {
            if (field.isEmpty()) {
                for (i in 1..26) {
                    field.add((96 + i).toChar())
                }
            }
            return field
        }

    lateinit var row1: LinearLayout

    lateinit var row2: LinearLayout

    //enable to continue
    var done = false

    private val baseMargin = DeviceUtils.toPx(ContextManager.getContext(), 5)

    fun init() {
        orientation = VERTICAL
        row1 = createRow()
        addView(row1)
        row2 = createRow(baseMargin)
        addView(row2)
    }


    fun initSourceData(arr: MutableList<String>) {
        process = 0
        source.clear()
        source.addAll(arr.map { it.toLowerCase() })

        fullWords = ""
        source.forEach {
            fullWords += it
            for (i in it.indices) {
                val current = it[i]
                if (!currentCharPool.contains(current)) {
                    currentCharPool.add(current)
                }
            }
        }
        refresh()
    }

    //刷新view内容
    private fun refresh() {
        try {
            val source = getCurrentChars()
            for (i in 0 until row1.childCount) {
                (row1.getChildAt(i) as TextView).text = source[i].toString()
            }
            if (source.size > MAX / 2) {
                for (i in 0 until row1.childCount) {
                    (row2.getChildAt(i) as TextView).text = source[i + 4].toString()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "failed refresh: ${e.message.toString()}")
        }
    }

    private fun getCurrentChars(): ArrayList<Char> {
        val result = arrayListOf<Char>()
        val currentChar = fullWords[process]
        val sourcePool = arrayListOf<Char>()
        sourcePool.addAll(currentCharPool)
        val plusPool = arrayListOf<Char>()
        plusPool.addAll(fullCharPool.filter { !currentCharPool.contains(it) })
        while (result.size < MAX) {
            //最后坑位没有答案就强行设置
            if (result.size == MAX - 1 && !result.contains(currentChar)) {
                result.add(currentChar)
                break
            }
            //get random char from pool
            val pool = if (sourcePool.size > 0) sourcePool else plusPool
            val randomIndex = (Math.random() * 100).toInt() % pool.size
            val target = pool[randomIndex]
            pool.remove(target)
            if (!result.contains(target)) {
                result.add(target)
            }
        }
        result.shuffle()
        return result
    }

    private fun createRow(marginTop: Int = 0): LinearLayout {
        val layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            resources.getDimension(R.dimen.keyboard_row_height).toInt()
        )
        layoutParams.setMargins(baseMargin / 2, marginTop, baseMargin / 2, 0)
        val row = LinearLayout(context)
        row.orientation = HORIZONTAL
        row.layoutParams = layoutParams
        bindRowData(arrayListOf("", "", "", ""), row)
        return row
    }

    private fun bindRowData(data: ArrayList<String>, container: LinearLayout) {
        container.removeAllViews()
        val layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        val radius = DeviceUtils.toPx(context, baseMargin / 2)
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
                if (done) return@setOnClickListener
                add(tv.text.toString())
            }
            container.addView(tv)
        }
    }

    fun add(text: String) {
        if (fullWords[process].toString() == text) {
            inputText += text
            listner?.onTextChange(inputText)
            listner?.onSuccess()
            if (process == fullWords.length - 1) {
                done = true
                listner?.onFinish(true)
            } else {
                process++
                refresh()
            }
        } else {
            listner?.onError()
            errorProcess++
            if (errorProcess == MAX_ERROR) {
                done = true
                listner?.onFinish(false)
            }
        }
    }

    interface InputListener {
        fun onTextChange(text: String)
        fun onError() {}
        fun onSuccess() {}
        fun onFinish(result: Boolean) {}
    }

}


/**
 * TODO
 * 我是main入口函数
 */
fun main(args: Array<String>) {
    val sourcePool = arrayListOf<Char>()
    for (i in 1..9) {
        sourcePool.add((96 + i).toChar())
    }
    val result = arrayListOf<Char>()
    while (result.size < 8) {
        val randomIndex = (Math.random() * 100).toInt() % sourcePool.size
        val target = sourcePool[randomIndex]
        sourcePool.remove(target)
        result.add(target)
    }
    result.forEach {
        print(it.toString() + " ")
    }

}
