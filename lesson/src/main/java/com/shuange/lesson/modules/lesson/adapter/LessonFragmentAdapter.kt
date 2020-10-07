package com.shuange.lesson.modules.lesson.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

open class LessonFragmentAdapter(
    fragmentActivity: FragmentActivity,
    val fragments: MutableList<Fragment>,
    val countGetter: (() -> Int)? = null
) :
    FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return countGetter?.invoke() ?: fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}