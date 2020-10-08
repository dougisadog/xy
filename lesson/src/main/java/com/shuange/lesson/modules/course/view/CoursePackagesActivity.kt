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
import com.shuange.lesson.databinding.ActivityCourseInfoBinding
import com.shuange.lesson.modules.course.adapter.CoursePackageAdapter
import com.shuange.lesson.modules.course.viewmodel.CourseInfoViewModel
import com.shuange.lesson.view.NonDoubleClickListener

/**
 * 课程包列表
 */
class CoursePackagesActivity : BaseActivity<ActivityCourseInfoBinding, CourseInfoViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, CoursePackagesActivity::class.java)
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

    private val coursePackageAdapter: CoursePackageAdapter by lazy {
        CoursePackageAdapter(
            layout = R.layout.layout_course_info_item,
            data = viewModel.courses
        )
    }

    override fun initView() {
        binding.title.text = viewModel.title.value
        viewModel.loadData()
        initCourses()
        initListener()
    }


    fun initCourses() {
        with(binding.rv) {
            layoutManager =
                LinearLayoutManager(this@CoursePackagesActivity, RecyclerView.VERTICAL, false)
            coursePackageAdapter.setOnItemClickListener { adapter, view, position ->
                val current = coursePackageAdapter.data[position]
                CourseLessonsActivity.start(current, this@CoursePackagesActivity)
            }
            adapter = coursePackageAdapter
        }
    }

    private fun initListener() {
        binding.back.setOnClickListener(NonDoubleClickListener {
            //TODO
            viewModel.loadData()
//            finish()
        })
    }

    override fun initViewObserver() {
    }


}