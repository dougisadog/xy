package com.shuange.lesson.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.shuange.lesson.R
import com.shuange.lesson.enumeration.CourseState
import com.shuange.lesson.modules.topquality.bean.TopQualityCourseBean
import com.shuange.lesson.utils.extension.colorValue
import corelib.util.ContextManager

object BindAdapters {

    @BindingAdapter("image")
    @JvmStatic
    fun setImage(iv: ImageView, image: String?) {
        image?.let {
            Glide.with(ContextManager.getContext()).load(it).into(iv)
        }
    }

    @BindingAdapter("roundImage")
    @JvmStatic
    fun setRoundImage(iv: ImageView, roundImage: String?) {
        roundImage?.let {
            Glide.with(ContextManager.getContext()).load(it).transform(CircleCrop()).into(iv)
        }
    }

    @BindingAdapter("courseState")
    @JvmStatic
    fun setCourseState(tv: TextView, courseState: CourseState?) {
        var textColor = R.color.hex_6F6F6F
        when (courseState) {
            CourseState.START -> {
                textColor = R.color.hex_9B9B9B
            }
            CourseState.IN_PROGRESS -> {
                textColor = R.color.hex_3AD08F
            }
            CourseState.FINISHED -> {
                textColor = R.color.hex_FC4848
            }
            CourseState.LOCKED -> {
                textColor = R.color.hex_6F6F6F
            }
        }
        tv.setTextColor(textColor.colorValue())
        tv.text = courseState?.text ?: ""
    }


    @BindingAdapter("visible")
    @JvmStatic
    fun setVisible(v: View, visible: Boolean?) {
        if (null == visible || visible == true) {
            v.visibility = View.VISIBLE
        } else {
            v.visibility = View.INVISIBLE
        }
    }

    @BindingAdapter("gone")
    @JvmStatic
    fun setGone(v: View, gone: Boolean?) {
        if (null == gone || gone == true) {
            v.visibility = View.GONE
        } else {
            v.visibility = View.VISIBLE
        }
    }

    @BindingAdapter("life")
    @JvmStatic
    fun setLife(v: TextView, life: Int?) {
        if (null != life) {
            v.text = life.toString()
        }
    }

    @BindingAdapter("freeTypeCourse")
    @JvmStatic
    fun setFreeTypeCourse(iv: ImageView, freeType: Int?) {
        freeType?.let {
            when (it) {
                TopQualityCourseBean.FREE_TYPE_GREEN -> {
                    Glide.with(ContextManager.getContext()).load(R.drawable.icon_home_gr)
                        .into(iv)
                }
                TopQualityCourseBean.FREE_TYPE_ORANGE -> {
                    Glide.with(ContextManager.getContext()).load(R.drawable.icon_home_or)
                        .into(iv)
                }
                TopQualityCourseBean.PAY_TYPE_STEAM -> {
                    Glide.with(ContextManager.getContext()).load(R.drawable.icon_image_zbkc)
                        .into(iv)
                }
                TopQualityCourseBean.PAID_TYPE -> {
                    Glide.with(ContextManager.getContext()).load(R.drawable.icon_home_or)
                        .into(iv)
                }
                else -> {

                }
            }
        }
    }
}