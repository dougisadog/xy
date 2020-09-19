package com.shuange.lesson.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.shuange.lesson.R


class CustomRoundAngleImageView :
    AppCompatImageView {
    var width = 0f
    var height = 0f

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        width = getWidth().toFloat()
        height = getHeight().toFloat()
    }

    private val defaultRadius = 0f
    private var radius = 0f
    private var leftTopRadius = 0f
    private var rightTopRadius = 0f
    private var rightBottomRadius = 0f
    private var leftBottomRadius = 0f

    private fun init(context: Context, attrs: AttributeSet) {
        // 读取配置
        val array = context.obtainStyledAttributes(attrs, R.styleable.Custom_Round_Image_View)
        radius =
            array.getDimensionPixelOffset(
                R.styleable.Custom_Round_Image_View_radius,
                defaultRadius.toInt()
            )
                .toFloat()
        leftTopRadius = array.getDimensionPixelOffset(
            R.styleable.Custom_Round_Image_View_left_top_radius,
            defaultRadius.toInt()
        ).toFloat()
        rightTopRadius = array.getDimensionPixelOffset(
            R.styleable.Custom_Round_Image_View_right_top_radius,
            defaultRadius.toInt()
        ).toFloat()
        rightBottomRadius = array.getDimensionPixelOffset(
            R.styleable.Custom_Round_Image_View_right_bottom_radius,
            defaultRadius.toInt()
        ).toFloat()
        leftBottomRadius = array.getDimensionPixelOffset(
            R.styleable.Custom_Round_Image_View_left_bottom_radius,
            defaultRadius.toInt()
        ).toFloat()
        Log.e("radius -->", "$radius")

        //如果四个角的值没有设置，那么就使用通用的radius的值。
        if (defaultRadius == leftTopRadius) {
            leftTopRadius = radius
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = radius
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = radius
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = radius
        }
        array.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        //这里做下判断，只有图片的宽高大于设置的圆角距离的时候才进行裁剪
        val maxLeft: Float = Math.max(leftTopRadius, leftBottomRadius)
        val maxRight: Float = Math.max(rightTopRadius, rightBottomRadius)
        val minWidth = maxLeft + maxRight
        val maxTop: Float = Math.max(leftTopRadius, rightTopRadius)
        val maxBottom: Float = Math.max(leftBottomRadius, rightBottomRadius)
        val minHeight = maxTop + maxBottom
        if (width >= minWidth && height > minHeight) {
            val path = Path()
            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius, 0f)
            path.lineTo(width - rightTopRadius, 0f)
            path.quadTo(width, 0f, width, rightTopRadius)
            path.lineTo(width, height - rightBottomRadius)
            path.quadTo(width, height, width - rightBottomRadius, height)
            path.lineTo(leftBottomRadius, height)
            path.quadTo(0f, height, 0f, height - leftBottomRadius)
            path.lineTo(0f, leftTopRadius)
            path.quadTo(0f, 0f, leftTopRadius, 0f)
            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }

}