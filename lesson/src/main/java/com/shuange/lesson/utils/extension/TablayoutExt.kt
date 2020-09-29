package com.shuange.lesson.utils.extension

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.shuange.lesson.R

fun TabLayout.init(vp:ViewPager2, titles:List<String>) {
   this.tabMode = TabLayout.MODE_FIXED
   this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (null == tab) return
            val context = vp.context
            val tv = TextView(context)
            tv.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            tv.gravity = Gravity.CENTER
            tv.text = tab.text
            tv.setTextColor(ContextCompat.getColor(context, R.color.hex_2FD393))
            tv.textSize = 18f
            tab.customView = tv
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.customView = null
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

    })
    TabLayoutMediator(
       this, vp
    ) { tab, position ->
        tab.text = titles[position]
    }.attach()
}