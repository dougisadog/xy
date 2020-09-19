package com.shuange.lesson.modules.course.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentCourseSubListBinding
import com.shuange.lesson.modules.course.viewmodel.CourseSubIntroductionViewModel
import com.shuange.lesson.modules.course.viewmodel.CourseSubViewModel

class CourseSubIntroductionFragment :
    BaseFragment<FragmentCourseSubListBinding, CourseSubIntroductionViewModel>() {

    override fun initView() {
        viewModel.loadData()
        viewModel.introduction.value = courseSubViewModel.content
    }


    override fun initViewObserver() {
    }

    private val courseSubViewModel: CourseSubViewModel by activityViewModels {
        BaseShareModelFactory()
    }

    override val viewModel: CourseSubIntroductionViewModel by viewModels {
        BaseShareModelFactory()
    }
    override val layoutId: Int
        get() = R.layout.fragment_course_sub_introduction
    override val viewModelId: Int
        get() = BR.courseSubIntroductionViewModel
}