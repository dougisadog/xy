package com.shuange.lesson.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shuange.lesson.base.BaseFragment

class BaseFragmentAdapter :
    FragmentStateAdapter {
    val fragments: MutableList<Fragment>

    constructor(
        fragmentActivity: FragmentActivity,
        fragments: MutableList<Fragment>
    ) : super(fragmentActivity) {
        this.fragments = fragments
    }

    constructor(
        fragment: Fragment,
        fragments: MutableList<Fragment>
    ) : super(fragment) {
        this.fragments = fragments
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}
