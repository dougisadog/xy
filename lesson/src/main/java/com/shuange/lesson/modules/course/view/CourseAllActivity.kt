package com.shuange.lesson.modules.course.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityCourseAllBinding
import com.shuange.lesson.modules.course.viewmodel.CourseAllViewModel
import com.shuange.lesson.modules.topquality.view.TopQualityCourseFragment
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.bind
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 全部课程
 */
class CourseAllActivity : BaseActivity<ActivityCourseAllBinding, CourseAllViewModel>() {

    companion object {
        fun start(context: Context, defaultType: Int? = null) {
            val i = Intent(context, CourseAllActivity::class.java)
            defaultType?.let {
                i.putExtra(IntentKey.COURSE_TYPE, defaultType)
            }
            context.startActivity(i)
        }
    }

    override val viewModel: CourseAllViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_course_all
    override val viewModelId: Int
        get() = BR.courseAllViewModel

    lateinit var fragmentAdapter: BaseFragmentAdapter

    override fun initParams() {
        super.initParams()
        intent.getIntExtra(IntentKey.COURSE_TYPE, -1).let {
            if (it != -1) {
                viewModel.defaultTypeId = it
            }
        }
    }

    override fun initView() {
        binding.header.title.text = "全部课程"
        viewModel.loadTypes {
            initViewPager()
            initTabIndicator()
        }
        initListener()
    }

    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        viewModel.pager.forEach {
            fragments.add(TopQualityCourseFragment.newInstance(it.first))
        }
        fragmentAdapter = BaseFragmentAdapter(this, fragments)
        with(binding.vp) {
            adapter = fragmentAdapter
            setCurrentItem(viewModel.getDefaultPageIndex())
        }
    }

    private fun initTabIndicator() {
//        binding.tabTl.init(binding.vp, viewModel.pager)
        binding.indicators.bind(binding.vp, viewModel.pager.map {
            it.second
        }.toMutableList())

    }

    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
    }

    fun search(text: String) {
        ToastUtil.show(text)
    }

    override fun initViewObserver() {
    }


}