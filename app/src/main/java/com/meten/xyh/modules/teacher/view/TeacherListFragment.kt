package com.meten.xyh.modules.teacher.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentTeacherListBinding
import com.meten.xyh.modules.discovery.adapter.TeacherAdapter
import com.meten.xyh.modules.teacher.viewmodel.TeacherListViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 名师推荐
 */
class TeacherListFragment : BaseFragment<FragmentTeacherListBinding, TeacherListViewModel>() {

    companion object {
        fun newInstance(): TeacherListFragment {
            val f = TeacherListFragment()
            Bundle().apply {
                f.arguments = this
            }
            return f
        }
    }

    override val viewModel: TeacherListViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val teacherAdapter: TeacherAdapter by lazy {
        TeacherAdapter(
            layout = R.layout.layout_teacher_list_item,
            data = viewModel.teachers
        )
    }

    override val layoutId: Int
        get() = R.layout.fragment_teacher_list
    override val viewModelId: Int
        get() = BR.teacherListViewModel


    override fun initView() {
        viewModel.loadData()
        initTeachers()
        initListener()
    }


    fun initTeachers() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                requireContext(),
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            teacherAdapter.setOnItemClickListener { adapter, view, position ->
                TeacherInfoActivity.start(
                    requireContext(),
                    teacherAdapter.data[position].id
                )
            }
            adapter = teacherAdapter
        }
    }

    private fun initListener() {
        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.loadTeachers {
                    binding.srl.finishLoadMore()
                }

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel.loadTeachers("0") {
                    binding.srl.finishRefresh()
                }

            }
        })

    }

    fun search(text: String) {
        viewModel.searchText.value = text
        viewModel.loadTeachers("0")
    }

    override fun initViewObserver() {
    }


}