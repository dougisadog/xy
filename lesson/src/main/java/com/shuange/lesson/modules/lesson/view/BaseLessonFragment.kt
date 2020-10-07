package com.shuange.lesson.modules.lesson.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.lesson.bean.LessonBean
import com.shuange.lesson.modules.lesson.other.LessonType
import com.shuange.lesson.modules.lesson.viewmodel.BaseLessonViewModel
import com.shuange.lesson.modules.lesson.viewmodel.LessonViewModel
import com.shuange.lesson.utils.Cancelable
import com.shuange.lesson.utils.MediaPlayerMgr


@Cancelable
abstract class BaseLessonFragment<BD : ViewDataBinding, VM : BaseLessonViewModel> :
    BaseFragment<BD, VM>() {

    var isFinished = false

    companion object {
        fun newInstance(lessonBean: LessonBean): BaseLessonFragment<*, *>? {
            var f: BaseLessonFragment<*, *>? = null
            when (lessonBean.lessonType) {
                LessonType.TYPE_01 -> {
                    f = NormalLessonFragment()
                }
                LessonType.TYPE_02 -> {
                    f = ReadingLessonFragment()
                }
                LessonType.TYPE_03, LessonType.TYPE_07 -> {
                    f = SelectorLessonFragment()
                }
                LessonType.TYPE_05, LessonType.TYPE_06, LessonType.TYPE_08, LessonType.TYPE_13, LessonType.TYPE_14 -> {
                    f = SelectorPicLessonFragment()
                }
                LessonType.TYPE_10 -> {
                    f = InputLessonFragment()
                }
                LessonType.TYPE_15 -> {
                    f = VideoLessonFragment()
                }
                LessonType.TYPE_16 -> {
                    f = RecordingLessonFragment()
                }
            }
            Bundle().apply {
                putSerializable(IntentKey.LESSON_TYPE_KEY, lessonBean)
                f.arguments = this
            }
            return f
        }
    }

    val lessonViewModel: LessonViewModel by activityViewModels {
        BaseShareModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, null, true)
        binding.setVariable(viewModelId, viewModel)
        return binding.root
    }

    override fun initParams() {
        super.initParams()
        (arguments?.getSerializable(IntentKey.LESSON_TYPE_KEY) as? LessonBean)?.let {
            viewModel.lessonBean = it
        }
    }

    override fun initView() {
    }

    fun playAudio() {
        val path = viewModel.lessonBean?.audio?.getFullPath()
        MediaPlayerMgr.getInstance().playMp(path)
    }

    override fun initViewObserver() {
        viewModel.done.observe(this, Observer {

        })
    }

    fun next(isDelay: Boolean = false) {
        val currentIndex = lessonViewModel.lessons.indexOf(viewModel.lessonBean)
        if (-1 == currentIndex) return
        val task = {
            lessonViewModel.next.value = currentIndex + 1
//            viewModel.saveLessonProcess()
        }
        if (isDelay) {
            android.os.Handler().postDelayed({
                task.invoke()
            }, 3000L)
        } else {
            task.invoke()
        }
    }
}