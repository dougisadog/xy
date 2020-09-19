package com.shuange.lesson.modules.course.view

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.databinding.ActivityCollectionBinding
import com.shuange.lesson.databinding.ActivityCourseListBinding
import com.shuange.lesson.enumeration.CourseState
import com.shuange.lesson.modules.course.adapter.CourseListAdapter
import com.shuange.lesson.modules.course.viewmodel.CourseListViewModel
import com.shuange.lesson.modules.topquality.viewmodel.TopQualityViewModel
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.view.dialog.CommonDialog
import kotlinx.android.synthetic.main.layout_header.view.*

class CourseListActivity : BaseActivity<ActivityCourseListBinding, CourseListViewModel>() {

    override val viewModel: CourseListViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val viewModelId: Int
        get() = BR.courseListViewModel

    override val layoutId: Int
        get() = R.layout.activity_course_list

    override var fragmentContainerId: Int? = R.id.fragmentContainerFl

    private val courseListAdapter: CourseListAdapter by lazy {
        CourseListAdapter(
            data = viewModel.courses
        )
    }

    override fun initView() {
        binding.header.title.text = "课程名称"
        initCourses()
        initListener()
    }


    fun initCourses() {
        with(binding.rv) {
            layoutManager =
                LinearLayoutManager(this@CourseListActivity, RecyclerView.VERTICAL, false)
            courseListAdapter.setOnItemClickListener { adapter, view, position ->
                val current = courseListAdapter.data[position]
                if (current.state == CourseState.LOCKED) {
                    CommonDialog(this@CourseListActivity).apply {
                        contentText = "请先做完前面的内容，才可以解锁此课程哦！"
                    }.show()
                } else {
                    com.shuange.lesson.utils.ToastUtil.show("item click stream:${courseListAdapter.data[position].name}")
                }
            }
            adapter = courseListAdapter
        }
    }

    private fun initListener() {
        binding.header.back.setOnClickListener(NonDoubleClickListener {
            finish()
        })
    }

    override fun initViewObserver() {
    }


}