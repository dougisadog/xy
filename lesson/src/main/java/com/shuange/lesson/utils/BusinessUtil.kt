package com.shuange.lesson.utils

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import com.shuange.lesson.R
import com.shuange.lesson.enumeration.LessonPackageType
import com.shuange.lesson.modules.course.view.CourseLessonsActivity
import com.shuange.lesson.modules.topquality.bean.CourseBean

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
//        target.removeAllViews()
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
            score > 0.6 -> {
                3
            }
            score > 0.4 -> {
                2
            }
            else -> {
                1
            }
        }
    }

    fun startCourse(context: Context, courseBean: CourseBean) {
        when (courseBean.packageType) {
            LessonPackageType.BASE -> {
                CourseLessonsActivity.start(courseBean, context)
            }
            LessonPackageType.VIDEO -> {
//                MediaCourseActivity.start(courseBean, context)
            }
        }
    }
}