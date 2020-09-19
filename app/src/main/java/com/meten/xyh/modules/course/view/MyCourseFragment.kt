package com.meten.xyh.modules.course.view

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.meten.xyh.R
import com.meten.xyh.BR
import com.meten.xyh.databinding.FragmentMyCourseBinding
import com.meten.xyh.modules.course.viewmodel.MyCourseViewModel
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 我的课程
 */
class MyCourseFragment : BaseFragment<FragmentMyCourseBinding, MyCourseViewModel>() {

    override val viewModel: MyCourseViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.fragment_my_course
    override val viewModelId: Int
        get() = BR.myCourseViewModel

    lateinit var fragmentAdapter: BaseFragmentAdapter


    override fun initView() {
        binding.header.title.text = "我的课程"
        binding.header.back.visibility = View.GONE
        viewModel.loadData()
        initListener()
        initViewPager()
        initTabIndicator()
    }

    private fun initViewPager() {
        val fragments = arrayListOf<BaseFragment<*, *>>()
        fragments.add(StreamCourseListFragment())
        fragments.add(OnlineCourseListFragment())
        fragmentAdapter = BaseFragmentAdapter(this, fragments)
        with(binding.vp) {
            adapter = fragmentAdapter
        }
    }

    private fun initTabIndicator() {
        binding.tabTl.tabMode = TabLayout.MODE_FIXED
        binding.tabTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (null == tab) return
                val tv = TextView(requireContext())
                tv.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                tv.gravity = Gravity.CENTER
                tv.text = tab.text
                tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.hex_2FD393))
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
            binding.tabTl, binding.vp
        ) { tab, position ->
            tab.text = viewModel.pager[position]
        }.attach()
    }

    private fun initListener() {
    }

    override fun initViewObserver() {
    }


}