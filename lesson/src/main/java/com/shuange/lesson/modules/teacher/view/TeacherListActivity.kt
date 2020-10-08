package com.shuange.lesson.modules.teacher.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityTeacherListBinding
import com.shuange.lesson.modules.teacher.adapter.TeacherAdapter
import com.shuange.lesson.modules.teacher.viewmodel.TeacherListViewModel
import com.shuange.lesson.utils.extension.setOnSearchListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 名师推荐
 */
class TeacherListActivity : BaseActivity<ActivityTeacherListBinding, TeacherListViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, TeacherListActivity::class.java)
            context.startActivity(i)
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
        get() = R.layout.activity_teacher_list
    override val viewModelId: Int
        get() = BR.teacherListViewModel


    override fun initView() {
        viewModel.loadData()
        binding.header.title.text = "名师推荐"
        initTeachers()
        initListener()
    }


    fun initTeachers() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@TeacherListActivity,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            teacherAdapter.setOnItemClickListener { adapter, view, position ->
                TeacherInfoActivity.start(
                    this@TeacherListActivity,
                    teacherAdapter.data[position].id
                )
            }
            adapter = teacherAdapter
        }
    }

    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
        binding.searchEt.setOnSearchListener {
            search(it.trim())
        }
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
        viewModel.loadTeachers("0")
    }

    override fun initViewObserver() {
    }


}