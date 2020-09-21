package com.shuange.lesson.modules.topquality.view

import androidx.fragment.app.viewModels
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentTopQualityCourseBinding
import com.shuange.lesson.modules.course.view.CourseInfoActivity
import com.shuange.lesson.modules.topquality.adapter.TopQualityAdapter
import com.shuange.lesson.modules.topquality.viewmodel.TopQualityCourseViewModel

class TopQualityCourseFragment :
    BaseFragment<FragmentTopQualityCourseBinding, TopQualityCourseViewModel>() {

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

    override fun initView() {
        viewModel.loadData()
        initCourses()
    }


    override fun initViewObserver() {
    }

    private fun initCourses() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            topQualityAdapter.setOnItemClickListener { adapter, view, position ->
                CourseInfoActivity.start(requireContext())
            }
            adapter = topQualityAdapter
        }
    }


}