package com.shuange.lesson.modules.course.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityCourseListBinding
import com.shuange.lesson.modules.course.adapter.CourseModuleAdapter
import com.shuange.lesson.modules.course.viewmodel.CourseListViewModel
import com.shuange.lesson.modules.lesson.view.LessonActivity
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 模块列表（课程详情）
 */
class CourseModulesActivity : BaseActivity<ActivityCourseListBinding, CourseListViewModel>() {

    companion object {
        fun start(context: Context, lessonId: Long, title: String) {
            val i = Intent(context, CourseModulesActivity::class.java)
            i.putExtra(IntentKey.LESSON_ID, lessonId)
            i.putExtra(IntentKey.LESSON_TITLE, title)
            context.startActivity(i)
        }
    }

    override val viewModel: CourseListViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val viewModelId: Int
        get() = BR.courseListViewModel

    override val layoutId: Int
        get() = R.layout.activity_course_list

    override var fragmentContainerId: Int? = R.id.fragmentContainerFl

    private val courseModuleAdapter: CourseModuleAdapter by lazy {
        CourseModuleAdapter(
            data = viewModel.modules
        )
    }

    override fun initParams() {
        super.initParams()
        viewModel.lessonId = intent.getLongExtra(IntentKey.LESSON_ID, 0).toString()
        viewModel.title = intent.getStringExtra(IntentKey.LESSON_TITLE) ?: ""
    }

    override fun initView() {
        binding.header.title.text = viewModel.title
        viewModel.loadData()
        initCourses()
        initListener()
    }


    fun initCourses() {
        with(binding.rv) {
            layoutManager =
                LinearLayoutManager(this@CourseModulesActivity, RecyclerView.VERTICAL, false)
            courseModuleAdapter.setOnItemClickListener { adapter, view, position ->
                val current = courseModuleAdapter.data[position]
                LessonActivity.start(this@CourseModulesActivity, current.moduleId, current.record?.progressIndex?:0)
            }
            adapter = courseModuleAdapter
        }
    }

    private fun initListener() {
        binding.header.back.setOnClickListener(NonDoubleClickListener {
            finish()
        })
        binding.straightCourseCl.setOnClickListener(NonDoubleClickListener {
            LessonActivity.start(this, viewModel.lastModuleId, viewModel.lastQuestionIndex)
        })
    }

    override fun initViewObserver() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }


}