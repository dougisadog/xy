package com.shuange.lesson.utils

import android.widget.ImageView
import android.widget.LinearLayout
import com.shuange.lesson.R

object BusinessUtil {

    fun refreshResult(
        target: LinearLayout,
        stars: Int,
        max: Int = 3,
        width: Int,
        offsetIndex: Int = 0
    ) {
        val params = LinearLayout.LayoutParams(width, width)
        params.setMargins(1, 0, 1, 0)
        target.removeAllViews()
        for (i in 0 until max) {
            val img = ImageView(target.context)
            img.layoutParams = params
            if (stars > i) {
                img.setImageResource(R.drawable.icon_star_on)
            } else {
                img.setImageResource(R.drawable.icon_star_off)
            }
            target.addView(img, target.childCount - offsetIndex)
        }
    }

    fun getStartsByScore(score: Double): Int {
        return when {
            score > 80.0 -> {
                3
            }
            score > 60.0 -> {
                2
            }
            score > 40.0 -> {
                1
            }
            else -> {
                0
            }
        }
    }
}