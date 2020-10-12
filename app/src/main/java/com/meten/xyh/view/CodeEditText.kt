package com.meten.xyh.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import com.meten.xyh.R


/**
 * 验证码输入框,重写EditText的绘制方法实现。
 * @author RAE
 */
class CodeEditText(context: Context, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {
    private var mTextColor = 0

    interface OnTextFinishListener {
        fun onTextFinish(text: CharSequence?, length: Int)
    }

    // 输入的最大长度
    private var mMaxLength = 4

    // 边框宽度
    private var mStrokeWidth = 0

    // 边框高度
    private var mStrokeHeight = 0

    // 边框之间的距离
    private var mStrokePadding = 20
    private val mRect = Rect()

    /**
     * 输入结束监听
     */
    private var mOnInputFinishListener: OnTextFinishListener? = null

    // 方框的背景
    lateinit var mStrokeDrawable: Drawable

    /**
     * 构造方法
     *
     */
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CodeEditText)
        val indexCount = typedArray.indexCount
        for (i in 0 until indexCount) {
            val index = typedArray.getIndex(i)
            if (index == R.styleable.CodeEditText_strokeHeight) {
                mStrokeHeight = typedArray.getDimension(index, 60f).toInt()
            } else if (index == R.styleable.CodeEditText_strokeWidth) {
                mStrokeWidth = typedArray.getDimension(index, 60f).toInt()
            } else if (index == R.styleable.CodeEditText_strokePadding) {
                mStrokePadding = typedArray.getDimension(index, 20f).toInt()
            } else if (index == R.styleable.CodeEditText_strokeBackground) {
                mStrokeDrawable = typedArray.getDrawable(index)
                    ?: throw NullPointerException("stroke drawable not allowed to be null!")
            } else if (index == R.styleable.CodeEditText_strokeLength) {
                mMaxLength = typedArray.getInteger(index, 4)
            }
        }
        typedArray.recycle()
        setMaxLength(mMaxLength)
        isLongClickable = false
        // 去掉背景颜色
        setBackgroundColor(Color.TRANSPARENT)
        // 不显示光标
        isCursorVisible = false
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        return false
    }
    //    private int px(int size) {
    //        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics());
    //    }
    /**
     * 设置最大长度
     */
    private fun setMaxLength(maxLength: Int) {
        filters = if (maxLength >= 0) {
            arrayOf<InputFilter>(LengthFilter(maxLength))
        } else {
            arrayOfNulls(0)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measuredWidth
        var height = measuredHeight
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        // 判断高度是否小于推荐高度
        if (height < mStrokeHeight) {
            height = mStrokeHeight
        }

        // 判断高度是否小于推荐宽度
        val recommendWidth = mStrokeWidth * mMaxLength + mStrokePadding * (mMaxLength - 1)
        if (width < recommendWidth) {
            width = recommendWidth
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode)
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        mTextColor = currentTextColor
        setTextColor(Color.TRANSPARENT)
        super.onDraw(canvas)
        setTextColor(mTextColor)
        // 重绘背景颜色
        drawStrokeBackground(canvas)
        // 重绘文本
        drawText(canvas)
    }

    /**
     * 重绘背景
     */
    private fun drawStrokeBackground(canvas: Canvas) {
        // 绘制方框背景颜色
        mRect.left = 0
        mRect.top = 0
        mRect.right = mStrokeWidth
        mRect.bottom = mStrokeHeight
        val count = canvas.saveCount
        canvas.save()
        for (i in 0 until mMaxLength) {
            mStrokeDrawable!!.bounds = mRect
            mStrokeDrawable.state = intArrayOf(android.R.attr.state_enabled)
            mStrokeDrawable.draw(canvas)
            val dx = mRect.right + mStrokePadding.toFloat()
            // 移动画布
            canvas.save()
            canvas.translate(dx, 0f)
        }
        canvas.restoreToCount(count)
        canvas.translate(0f, 0f)

        // 绘制激活状态的边框
        // 当前激活的索引
        val activatedIndex = Math.max(0, editableText.length)
        mRect.left = mStrokeWidth * activatedIndex + mStrokePadding * activatedIndex
        mRect.right = mRect.left + mStrokeWidth
        mStrokeDrawable!!.state = intArrayOf(android.R.attr.state_focused)
        mStrokeDrawable.bounds = mRect
        mStrokeDrawable.draw(canvas)
    }

    /**
     * 重绘文本
     */
    private fun drawText(canvas: Canvas) {
        val count = canvas.saveCount
        canvas.translate(0f, 0f)
        val length: Int = editableText.length
        for (i in 0 until length) {
            val text: String = java.lang.String.valueOf(editableText[i])
            val textPaint = paint
            textPaint.color = mTextColor
            // 获取文本大小
            textPaint.getTextBounds(text, 0, 1, mRect)
            // 计算(x,y) 坐标
            val x = mStrokeWidth / 2 + (mStrokeWidth + mStrokePadding) * i - mRect.centerX()
            val y = canvas.height / 2 + mRect.height() / 2
            canvas.drawText(text, x.toFloat(), y.toFloat(), textPaint)
        }
        canvas.restoreToCount(count)
    }

    override fun onTextChanged(
        text: CharSequence?, start: Int,
        lengthBefore: Int, lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        // 当前文本长度
        val textLength: Int = editableText.length
        if (textLength == mMaxLength) {
            hideSoftInput()
            if (mOnInputFinishListener != null) {
                mOnInputFinishListener!!.onTextFinish(editableText.toString(), mMaxLength)
            }
        }
    }

    fun hideSoftInput() {
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    /**
     * 设置输入完成监听
     */
    fun setOnTextFinishListener(onInputFinishListener: OnTextFinishListener?) {
        mOnInputFinishListener = onInputFinishListener
    }

}