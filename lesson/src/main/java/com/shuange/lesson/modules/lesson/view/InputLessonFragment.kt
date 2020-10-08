package com.shuange.lesson.modules.lesson.view

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.UnderlineSpan
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentInputLessonBinding
import com.shuange.lesson.modules.lesson.bean.InputData
import com.shuange.lesson.modules.lesson.viewmodel.InputLessonViewModel
import com.shuange.lesson.utils.extension.setCenterImage
import com.shuange.lesson.view.Keyboard8View


class InputLessonFragment :
    BaseLessonFragment<FragmentInputLessonBinding, InputLessonViewModel>() {

    override fun initParams() {
        super.initParams()
    }

    override fun initView() {
        super.initView()
        initContent()
        initKeyboard()
        initListener()
        viewModel.done.value = true
    }

    private fun initContent() {
        viewModel.questionBean?.let {
            it.inputData = InputData().apply {
                text = it.text
                words = it.selections.filter { selection -> selection.bingo }
                    .map { selection -> selection.text }.toMutableList()
            }
            binding.imageIv.setCenterImage(it)
            refreshTitle()
        }
    }

    private fun initKeyboard() {
        viewModel.questionBean?.inputData?.words?.let {
            binding.keyboard.initSourceData(it)
        }
    }

    private fun initListener() {
        binding.keyboard.listner = object : Keyboard8View.InputListener {
            override fun onTextChange(text: String) {
                viewModel.currentInput.value = text
            }

            override fun onFinish(result: Boolean) {
                super.onFinish(result)
                var score = 100
                if (!result) {
                    lessonViewModel.wrong.value = true
                    score = 0
                }
                next(answer = viewModel.currentInput.value ?: "", score = score)
            }
        }
    }

    private fun refreshTitle(text: String = "") {
        val inputData = viewModel.questionBean?.inputData ?: return


        val originText = inputData.text
        val words = inputData.words
        val ssb = SpannableStringBuilder(inputData.text)
        val sb = StringBuilder()

        //剩余未填写的输入长度
        var remainLength = text.length
        words.forEach {
            val startIndex = originText.indexOf(it)
            if (-1 != startIndex) {
                //当前单词需要空白(" ")的长度
                val emptyLength = Math.max(0, it.length - remainLength)
                remainLength = Math.max(0, remainLength - it.length)
                sb.clear()
                for (i in 0 until it.length) {
                    if (i < it.length - emptyLength) {
                        sb.append(it[i])
                    } else {
                        sb.append(" ")
                    }
                }
                ssb.replace(startIndex, startIndex + it.length, sb.toString())
                //设置当前单词的下划线
                ssb.setSpan(
                    UnderlineSpan(),
                    startIndex,
                    startIndex + it.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        binding.titleTv.text = ssb
    }

    override fun initViewObserver() {
        viewModel.currentInput.observe(this, Observer {
            refreshTitle(it)
        })
    }

    override val viewModel: InputLessonViewModel by viewModels {
        BaseShareModelFactory()
    }
    override val layoutId: Int
        get() = R.layout.fragment_input_lesson
    override val viewModelId: Int
        get() = BR.baseLessonViewModel
}