package com.shuange.lesson.modules.lesson.view

import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentSelectorLessonBinding
import com.shuange.lesson.modules.lesson.viewmodel.SelectorLessonViewModel
import com.shuange.lesson.utils.extension.setCenterImage
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.view.TextSelectionView
import corelib.util.DeviceUtils
import kotlinx.android.synthetic.main.layout_title.view.*


class SelectorLessonFragment :
    BaseLessonFragment<FragmentSelectorLessonBinding, SelectorLessonViewModel>() {

    override fun initView() {
        super.initView()
        initContent()
        initSelections()
        initListener()
    }

    private fun initContent() {
        viewModel.lessonBean?.let {
            binding.imageIv.setCenterImage(it)
            binding.topContainer.titleTv.text = it.text
        }
    }

    private fun initListener() {
    }

    override fun initViewObserver() {
    }

    private fun initSelections() {
        val height = DeviceUtils.toPx(requireContext(), 58.0)
        val bottomMargin = DeviceUtils.toPx(requireContext(), 12.0)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
        params.bottomMargin = bottomMargin
        val selections = viewModel.lessonBean?.selections
        selections?.forEach {
            val textSelectionView = TextSelectionView(requireContext())
            textSelectionView.layoutParams = params
            textSelectionView.setSelection(it)
            textSelectionView.setOnClickListener(NonDoubleClickListener {
                if (isFinished) return@NonDoubleClickListener
                textSelectionView.refreshState()
                if (!it.bingo) {
                    lessonViewModel.wrong.value = true
                }
                isFinished = true
                viewModel.done.value = true
                next(isDelay = true)
            })
            binding.selectionsLl.addView(textSelectionView)
        }
    }

    override val viewModel: SelectorLessonViewModel by viewModels {
        BaseShareModelFactory()
    }
    override val layoutId: Int
        get() = R.layout.fragment_selector_lesson
    override val viewModelId: Int
        get() = BR.selectorLessonViewModel
}