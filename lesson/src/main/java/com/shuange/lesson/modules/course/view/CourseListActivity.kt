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
import com.shuange.lesson.enumeration.CourseState
import com.shuange.lesson.modules.course.adapter.CourseListAdapter
import com.shuange.lesson.modules.course.viewmodel.CourseListViewModel
import com.shuange.lesson.modules.lesson.view.LessonActivity
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.view.dialog.CommonDialog
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 模块列表（课程详情）
 */
class CourseListActivity : BaseActivity<ActivityCourseListBinding, CourseListViewModel>() {

    companion object {
        fun start(context: Context, lessonId: String, title: String) {
            val i = Intent(context, CourseActivity::class.java)
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

    private val courseListAdapter: CourseListAdapter by lazy {
        CourseListAdapter(
            data = viewModel.courses
        )
    }

    override fun initParams() {
        super.initParams()
        viewModel.lessonId = intent.getStringExtra(IntentKey.LESSON_ID) ?: return
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
                LinearLayoutManager(this@CourseListActivity, RecyclerView.VERTICAL, false)
            courseListAdapter.setOnItemClickListener { adapter, view, position ->
                val current = courseListAdapter.data[position]
                if (current.state == CourseState.LOCKED) {
                    CommonDialog(this@CourseListActivity).apply {
                        contentText = "请先做完前面的内容，才可以解锁此课程哦！"
                    }.show()
                } else {
                    LessonActivity.start(this@CourseListActivity, current.courseId)
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