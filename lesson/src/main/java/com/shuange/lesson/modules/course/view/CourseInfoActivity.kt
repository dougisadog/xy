package com.shuange.lesson.modules.course.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityCourseBinding
import com.shuange.lesson.databinding.ActivityCourseInfoBinding
import com.shuange.lesson.modules.course.adapter.CourseInfoAdapter
import com.shuange.lesson.modules.course.viewmodel.CourseInfoViewModel
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 课程包列表
 */
class CourseInfoActivity : BaseActivity<ActivityCourseInfoBinding, CourseInfoViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, CourseInfoActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: CourseInfoViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val viewModelId: Int
        get() = BR.courseInfoViewModel

    override val layoutId: Int
        get() = R.layout.activity_course_info

    override var fragmentContainerId: Int? = R.id.fragmentContainerFl

    private val courseInfoAdapter: CourseInfoAdapter by lazy {
        CourseInfoAdapter(
            layout = R.layout.layout_course_info_item,
            data = viewModel.courses
        )
    }

    override fun initView() {
        binding.title.text = viewModel.title.value
        initCourses()
        initListener()
    }


    fun initCourses() {
        with(binding.rv) {
            layoutManager =
                LinearLayoutManager(this@CourseInfoActivity, RecyclerView.VERTICAL, false)
            courseInfoAdapter.setOnItemClickListener { adapter, view, position ->
                val current = courseInfoAdapter.data[position]
                CourseListActivity.start(this@CourseInfoActivity, current.courseId, current.title)
            }
            adapter = courseInfoAdapter
        }
    }

    private fun initListener() {
        binding.back.setOnClickListener(NonDoubleClickListener {
            finish()
        })
    }

    override fun initViewObserver() {
    }


}