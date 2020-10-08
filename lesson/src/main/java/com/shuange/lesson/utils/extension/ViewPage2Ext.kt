package com.shuange.lesson.utils.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.ImageFragment
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.adapter.RecyclePagerAdapter
import com.shuange.lesson.base.adapter.setRecycleAdapter
import com.shuange.lesson.base.adapter.starAuto

fun ViewPager2.initAdapter(context: FragmentActivity, data: MutableList<out BaseItemBean>) {
    if (data.size > 1) {
        val fragmentAdapter = RecyclePagerAdapter(context, data.map {
            it.image
        }.toMutableList()) {
            ImageFragment.newInstance(it)
        }
        setRecycleAdapter(fragmentAdapter)
        starAuto()
    } else {
        val fragments = mutableListOf<Fragment>()
        data.forEach {
            fragments.add(ImageFragment.newInstance(it.image))
        }
        val adapter = BaseFragmentAdapter(context, fragments)
        this.adapter = adapter
    }
}

fun ViewPager2.initAdapter(context: Fragment, data: MutableList<out BaseItemBean>) {
    if (data.size > 1) {
        val fragmentAdapter = RecyclePagerAdapter(context, data.map {
            it.image
        }.toMutableList()) {
            ImageFragment.newInstance(it)
        }
        setRecycleAdapter(fragmentAdapter)
        starAuto()
    } else {
        val fragments = mutableListOf<Fragment>()
        data.forEach {
            fragments.add(ImageFragment.newInstance(it.image))
        }
        val adapter = BaseFragmentAdapter(context, fragments)
        this.adapter = adapter
    }
}