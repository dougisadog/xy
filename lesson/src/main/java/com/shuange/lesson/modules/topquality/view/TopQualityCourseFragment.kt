package com.shuange.lesson.modules.topquality.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentTopQualityCourseBinding
import com.shuange.lesson.modules.course.view.CoursePackagesActivity
import com.shuange.lesson.modules.topquality.adapter.TopQualityAdapter
import com.shuange.lesson.modules.topquality.viewmodel.TopQualityCourseViewModel

class TopQualityCourseFragment :
    BaseFragment<FragmentTopQualityCourseBinding, TopQualityCourseViewModel>() {


    companion object {
        fun newInstance(courseType: Int): TopQualityCourseFragment {
            val f = TopQualityCourseFragment()
            Bundle().apply {
                putInt(IntentKey.COURSE_TYPE, courseType)
                f.arguments = this
            }
            return f
        }
    }

    override val viewModel: TopQualityCourseViewModel by viewModels {
        BaseShareModelFactory()
    }
    override val layoutId: Int
        get() = R.layout.fragment_top_quality_course
    override val viewModelId: Int
        get() = BR.topQualityCourseViewModel

    private val topQualityAdapter: TopQualityAdapter by lazy {
        TopQualityAdapter(
            layout = R.layout.layout_top_quality_item_with_count,
            data = viewModel.topQualityItems
        )
    }

    override fun initParams() {
        super.initParams()
        viewModel.courseType = arguments?.getInt(IntentKey.COURSE_TYPE)
    }

    override fun initView() {
        viewModel.loadData()
        initCourses()
        initListener()
    }

    private fun initListener() {
        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.loadCourses {
                    binding.srl.finishLoadMore()
                }

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel.loadCourses("0") {
                    binding.srl.finishRefresh()
                }

            }
        })
    }


    override fun initViewObserver() {
    }

    private fun initCourses() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            topQualityAdapter.setOnItemClickListener { adapter, view, position ->
                CoursePackagesActivity.start(requireContext())
            }
            adapter = topQualityAdapter
        }
    }

    fun search(text: String) {
        viewModel.searchText.value = text
        viewModel.loadCourses("0")
    }


}