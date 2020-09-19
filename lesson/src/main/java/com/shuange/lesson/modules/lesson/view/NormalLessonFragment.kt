package com.shuange.lesson.modules.lesson.view

import androidx.fragment.app.viewModels
import com.shuange.lesson.R
import com.shuange.lesson.BR
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentNormalLessonBinding
import com.shuange.lesson.modules.lesson.viewmodel.NormalLessonViewModel
import com.shuange.lesson.utils.extension.setCenterImage
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_title.view.*


class NormalLessonFragment :
    BaseLessonFragment<FragmentNormalLessonBinding, NormalLessonViewModel>() {

    override fun initParams() {
        super.initParams()
    }

    override fun initView() {
        super.initView()
        initContent()
        initListener()
        viewModel.done.value = true
    }

    private fun initContent() {
        viewModel.lessonBean?.let {
            binding.imageIv.setCenterImage(it)
            binding.topContainer.titleTv.text = it.text
        }
    }

    private fun initListener() {
        binding.topContainer.audioIv.setOnClickListener(NonDoubleClickListener {
            playAudio()
        })
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            (requireActivity() as LessonActivity).next()
        })
    }

    override fun initViewObserver() {
    }

    override val viewModel: NormalLessonViewModel by viewModels {
        BaseShareModelFactory()
    }
    override val layoutId: Int
        get() = R.layout.fragment_normal_lesson
    override val viewModelId: Int
        get() = BR.baseLessonViewModel
}