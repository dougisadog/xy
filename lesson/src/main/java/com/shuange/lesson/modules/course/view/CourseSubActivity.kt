package com.shuange.lesson.modules.course.view

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityCourseSubBinding
import com.shuange.lesson.modules.course.viewmodel.CourseSubViewModel
import kotlinx.android.synthetic.main.layout_header.view.*

class CourseSubActivity : BaseActivity<ActivityCourseSubBinding, CourseSubViewModel>() {

    override val viewModel: CourseSubViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val viewModelId: Int
        get() = BR.courseSubViewModel

    override val layoutId: Int
        get() = R.layout.activity_course_sub

    override var fragmentContainerId: Int? = R.id.fragmentContainerFl

    lateinit var fragmentAdapter: BaseFragmentAdapter


    override fun initView() {
        binding.header.title.text = viewModel.title.value
        viewModel.loadData()
        initListener()
        initViewPager()
        initTabIndicator()
    }

    private fun initViewPager() {
        val fragments = arrayListOf<BaseFragment<*, *>>()
        fragments.add(CourseSubListFragment())
        fragments.add(CourseSubIntroductionFragment())
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
                val tv = TextView(this@CourseSubActivity)
                tv.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                tv.gravity = Gravity.CENTER
                tv.text = tab.text
                tv.setTextColor(ContextCompat.getColor(this@CourseSubActivity, R.color.hex_2FD393))
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
        binding.header.back.setOnClickListener {
            finish()
        }
    }

    override fun initViewObserver() {
    }


}