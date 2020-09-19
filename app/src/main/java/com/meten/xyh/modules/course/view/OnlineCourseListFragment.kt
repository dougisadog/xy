package com.meten.xyh.modules.course.view

import androidx.fragment.app.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentMyCourseListBinding
import com.meten.xyh.modules.course.adapter.MyCourseListAdapter
import com.meten.xyh.modules.course.viewmodel.MyCourseListViewModel
import com.meten.xyh.modules.course.viewmodel.MyCourseViewModel
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory

/**
 * 线上课程
 */
class OnlineCourseListFragment : BaseFragment<FragmentMyCourseListBinding, MyCourseListViewModel>() {

    override val viewModel: MyCourseListViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val myCourseViewModel: MyCourseViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { BaseShareModelFactory() }
    )

    override val layoutId: Int
        get() = R.layout.fragment_my_course_list
    override val viewModelId: Int
        get() = BR.myCourseListViewModel


    private val myCourseListAdapter: MyCourseListAdapter by lazy {
        MyCourseListAdapter(
            layout = R.layout.layout_my_course_list_item,
            data = myCourseViewModel.onlineCourses
        )
    }

    override fun initView() {
        initListener()
        initCourses()
    }

    fun initCourses() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                requireContext(),
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            myCourseListAdapter.setOnItemClickListener { adapter, view, position ->
                com.shuange.lesson.utils.ToastUtil.show("item click  teacher:${myCourseListAdapter.data[position].title}")
            }
            adapter = myCourseListAdapter
        }
    }

    private fun initListener() {
    }

    override fun initViewObserver() {
    }


}