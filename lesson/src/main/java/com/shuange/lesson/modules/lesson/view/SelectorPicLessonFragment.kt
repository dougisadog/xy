package com.shuange.lesson.modules.lesson.view

import android.view.View
import androidx.fragment.app.viewModels
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentSelectorPicLessonBinding
import com.shuange.lesson.modules.lesson.bean.Selection
import com.shuange.lesson.modules.lesson.viewmodel.SelectorPicLessonViewModel
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.view.PicSelectionView
import kotlinx.android.synthetic.main.layout_title.view.*


class SelectorPicLessonFragment :
    BaseLessonFragment<FragmentSelectorPicLessonBinding, SelectorPicLessonViewModel>() {

    var selectionViews = arrayListOf<PicSelectionView>()

    override fun initView() {
        super.initView()
        initContent()
        initImageViews()
        initSelections()
        initListener()
    }

    private fun initContent() {
        viewModel.lessonBean?.let {
            binding.topContainer.titleTv.text = it.text
            if (null == it.audio) {
                binding.topContainer.audioIv.visibility = View.GONE
            }
        }
    }

    private fun initImageViews() {
        selectionViews.addAll(
            arrayListOf(
                binding.selection1Iv,
                binding.selection2Iv,
                binding.selection3Iv,
                binding.selection4Iv
            )
        )
        selectionViews.forEach {
            it.visibility = View.GONE
        }
    }

    private fun initListener() {
        if (null !=  viewModel.lessonBean?.audio) {
            binding.topContainer.audioIv.setOnClickListener(NonDoubleClickListener {
                playAudio()
            })
        }
    }

    override fun initViewObserver() {
    }

    private fun initSelections() {
        val finalSelections = arrayListOf<Selection?>()
        val selections = viewModel.lessonBean?.selections ?: return
        if (selections.size == 2) {
            finalSelections.add(selections[0])
            finalSelections.add(null)
            finalSelections.add(selections[1])
            finalSelections.add(null)
        }
        else {
            finalSelections.addAll(selections)
        }
        finalSelections.forEachIndexed { index, selection ->
            selection?.let {
                val selectionView = selectionViews[index].apply { visibility = View.VISIBLE }
                selectionView.setSelection(selection)
                selectionView.setOnClickListener(NonDoubleClickListener {
                    if (isFinished) return@NonDoubleClickListener
                    refreshSelections(selectionView)
                    isFinished = true
                    if (!selection.bingo) {
                        lessonViewModel.wrong.value = true
                    }
                    viewModel.done.value = true
                    next(isDelay = true)

                })
            }
        }
    }

    private fun refreshSelections(selectionView: PicSelectionView) {
        selectionViews.forEach {
            it.setPicAlpha(0.55f)
        }
        selectionView.setPicAlpha(1f)
        selectionView.refreshState()
    }

    override val viewModel: SelectorPicLessonViewModel by viewModels {
        BaseShareModelFactory()
    }
    override val layoutId: Int
        get() = R.layout.fragment_selector_pic_lesson
    override val viewModelId: Int
        get() = BR.selectorPicLessonViewModel
}