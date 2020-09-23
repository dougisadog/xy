package com.meten.xyh.modules.teacher.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityTeacherListBinding
import com.meten.xyh.modules.discovery.adapter.TeacherAdapter
import com.meten.xyh.modules.teacher.viewmodel.TeacherListViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil
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
                TeacherInfoActivity.start(this@TeacherListActivity, teacherAdapter.data[position].id)
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

    }

    fun search(text: String) {
        ToastUtil.show(text)
    }

    override fun initViewObserver() {
    }


}