package com.meten.xyh.modules.course.view

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentMyCourseBinding
import com.meten.xyh.modules.course.viewmodel.MyCourseViewModel
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.topquality.view.TopQualityCourseFragment
import com.shuange.lesson.utils.extension.bind
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
        val fragments = arrayListOf<Fragment>()
        fragments.add(TopQualityCourseFragment())
        fragments.add(StreamCourseListFragment())
        fragmentAdapter = BaseFragmentAdapter(this, fragments)
        with(binding.vp) {
            adapter = fragmentAdapter
        }
    }

    private fun initTabIndicator() {
//        binding.tabTl.init(binding.vp, viewModel.pager)
        binding.indicators.bind(binding.vp, viewModel.pager)

    }

    private fun initListener() {
    }

    override fun initViewObserver() {
    }


}