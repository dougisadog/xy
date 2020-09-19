package com.meten.xyh.base

import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.meten.xyh.R
import com.meten.xyh.modules.discovery.bean.TopQualityBean
import com.meten.xyh.view.TestPlusView
import com.shuange.lesson.modules.lesson.bean.SourceData
import com.shuange.lesson.view.NonDoubleClickListener
import corelib.util.ContextManager

object BindingAdapters {

    @BindingAdapter("testColor")
    @JvmStatic
    fun setTestColor(tv: ViewGroup, testColor: String?) {
        if (testColor == "red") {
            tv.setBackgroundColor(ContextCompat.getColor(ContextManager.getContext(), R.color.red))
        } else {
            tv.setBackgroundColor(
                ContextCompat.getColor(
                    ContextManager.getContext(),
                    R.color.white
                )
            )
        }
    }

    @BindingAdapter("roundImageFile")
    @JvmStatic
    fun setRoundImageFile(iv: ImageView, source: SourceData?) {
        source?.let {
            val file = it.getSource()
            if (file?.exists() == true) {
                Glide.with(ContextManager.getContext()).load(file).transform(CircleCrop()).into(iv)
            } else {
                Glide.with(ContextManager.getContext()).load(it.url).transform(CircleCrop())
                    .into(iv)
            }
        }
    }

    @BindingAdapter("freeType")
    @JvmStatic
    fun setFreeType(iv: ImageView, freeType: Int?) {
        freeType?.let {
            when (it) {
                TopQualityBean.FREE_TYPE_GREEN -> {
                    Glide.with(ContextManager.getContext()).load(R.drawable.icon_home_free_green)
                        .into(iv)
                }
                TopQualityBean.FREE_TYPE_ORANGE -> {
                    Glide.with(ContextManager.getContext()).load(R.drawable.icon_home_free_orange)
                        .into(iv)
                }
                else -> {

                }
            }
        }
    }


    @BindingAdapter("testPlus")
    @JvmStatic
    fun setTestPlus(view: TestPlusView, newValue: String?) {
        // Important to break potential infinite loops.
        if (view.realText != newValue) {
            view.realText = newValue ?: ""
        }
        view.text = view.realText
    }

    @InverseBindingAdapter(attribute = "testPlus", event = "testPlusAttrChanged")
    @JvmStatic
    fun getTestPlus(view: TestPlusView): String {
        return view.realText
    }

    @BindingAdapter("testPlusAttrChanged")
    @JvmStatic
    fun setTestPlusListener(
        view: TestPlusView,
        attrChange: InverseBindingListener
    ) {
        view.setOnClickListener(NonDoubleClickListener {
            view.realText = view.realText + "1"
            attrChange.onChange()
        })
    }
}