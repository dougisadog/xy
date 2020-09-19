package com.shuange.lesson.modules.course.view

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityCourseBinding
import com.shuange.lesson.enumeration.CourseState
import com.shuange.lesson.modules.course.adapter.CourseAdapter
import com.shuange.lesson.modules.course.viewmodel.CourseViewModel
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*

class CourseActivity : BaseActivity<ActivityCourseBinding, CourseViewModel>() {

    override val viewModel: CourseViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val viewModelId: Int
        get() = BR.courseViewModel

    override val layoutId: Int
        get() = R.layout.activity_course

    override var fragmentContainerId: Int? = R.id.fragmentContainerFl

    private val courseAdapter: CourseAdapter by lazy {
        CourseAdapter(
            layout = R.layout.layout_course_item,
            data = viewModel.courses
        )
    }

    override fun initView() {
        binding.header.title.text = viewModel.title.value
        initCourses()
        initListener()
    }


    fun initCourses() {
        with(binding.rv) {
            layoutManager = LinearLayoutManager(this@CourseActivity, RecyclerView.VERTICAL, false)
            courseAdapter.setOnItemClickListener { adapter, view, position ->
                val current = courseAdapter.data[position]
                if (current.state == CourseState.LOCKED) return@setOnItemClickListener
                ToastUtil.show("item click courseAdapter:${courseAdapter.data[position].name}")
            }
            adapter = courseAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun initListener() {
        binding.header.back.setOnClickListener(NonDoubleClickListener {
            finish()
        })
        binding.straightCourseCl.setOnClickListener(NonDoubleClickListener {
            ToastUtil.show("上次学到：4.学问位置》核心课程A")
        })
    }

    override fun initViewObserver() {
    }


}