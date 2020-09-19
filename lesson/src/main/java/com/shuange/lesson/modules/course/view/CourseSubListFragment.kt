package com.shuange.lesson.modules.course.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentCourseSubListBinding
import com.shuange.lesson.modules.course.adapter.CourseSubListAdapter
import com.shuange.lesson.modules.course.viewmodel.CourseSubListViewModel
import com.shuange.lesson.modules.course.viewmodel.CourseSubViewModel
import com.shuange.lesson.utils.ToastUtil

class CourseSubListFragment : BaseFragment<FragmentCourseSubListBinding, CourseSubListViewModel>() {

    private val courseSubListAdapter: CourseSubListAdapter by lazy {
        CourseSubListAdapter(
            data = viewModel.items
        )
    }

    override fun initView() {
        viewModel.loadData()
        viewModel.items.addAll(courseSubViewModel.items)
        initItems()
    }


    override fun initViewObserver() {
    }

    fun initItems() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            courseSubListAdapter.setOnItemClickListener { adapter, view, position ->
                ToastUtil.show("item click  CourseSub:${courseSubListAdapter.data[position].name}")
            }
            isNestedScrollingEnabled = false
            adapter = courseSubListAdapter
        }
    }

    private val courseSubViewModel: CourseSubViewModel by activityViewModels {
        BaseShareModelFactory()
    }

    override val viewModel: CourseSubListViewModel by viewModels {
        BaseShareModelFactory()
    }
    override val layoutId: Int
        get() = R.layout.fragment_course_sub_list
    override val viewModelId: Int
        get() = BR.courseSubListViewModel
}