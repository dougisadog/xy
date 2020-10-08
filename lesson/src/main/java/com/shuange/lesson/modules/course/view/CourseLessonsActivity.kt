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
import com.shuange.lesson.databinding.ActivityCourseBinding
import com.shuange.lesson.enumeration.CourseState
import com.shuange.lesson.modules.course.adapter.CourseLessonAdapter
import com.shuange.lesson.modules.course.viewmodel.CourseViewModel
import com.shuange.lesson.modules.lesson.view.LessonActivity
import com.shuange.lesson.modules.topquality.bean.CourseBean
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 课程列表 （课程包详情）
 */
class CourseLessonsActivity : BaseActivity<ActivityCourseBinding, CourseViewModel>() {

    companion object {
        fun start(bean: CourseBean, context: Context) {
            val i = Intent(context, CourseLessonsActivity::class.java)
            i.putExtra(IntentKey.LESSON_PACKAGE_ITEM, bean)
            context.startActivity(i)
        }
    }

    override val viewModel: CourseViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val viewModelId: Int
        get() = BR.courseViewModel

    override val layoutId: Int
        get() = R.layout.activity_course

    override var fragmentContainerId: Int? = R.id.fragmentContainerFl

    private val courseLessonAdapter: CourseLessonAdapter by lazy {
        CourseLessonAdapter(
            layout = R.layout.layout_course_item,
            data = viewModel.courses
        )
    }

    override fun initParams() {
        super.initParams()
        viewModel.courseBean = intent?.getSerializableExtra(IntentKey.LESSON_PACKAGE_ITEM) as? CourseBean
            ?: return
    }

    override fun initView() {
        binding.header.title.text = viewModel.courseBean?.title
        initCourses()
        initListener()
    }


    fun initCourses() {
        with(binding.rv) {
            layoutManager = LinearLayoutManager(this@CourseLessonsActivity, RecyclerView.VERTICAL, false)
            courseLessonAdapter.setOnItemClickListener { adapter, view, position ->
                val current = courseLessonAdapter.data[position]
                if (current.state == CourseState.LOCKED) return@setOnItemClickListener
                CourseModulesActivity.start(this@CourseLessonsActivity, current.lessonId, current.name)
            }
            adapter = courseLessonAdapter
            isNestedScrollingEnabled = false
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