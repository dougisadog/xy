package com.shuange.lesson.modules.lesson.view

import android.Manifest
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentReadingLessonBinding
import com.shuange.lesson.modules.lesson.viewmodel.ReadingLessonViewModel
import com.shuange.lesson.taioral.TAIOralManager
import com.shuange.lesson.taioral.startRecord
import com.shuange.lesson.taioral.stopRecord
import com.shuange.lesson.utils.BusinessUtil
import com.shuange.lesson.utils.PhraseMatcher
import com.shuange.lesson.utils.RecordManager
import com.shuange.lesson.utils.extension.setCenterImage
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.youdao.YoudaoParser
import corelib.util.DeviceUtils
import kotlinx.android.synthetic.main.layout_title.view.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File


@RuntimePermissions
open class ReadingLessonFragment :
    BaseLessonFragment<FragmentReadingLessonBinding, ReadingLessonViewModel>() {

    private val handler = Handler()

    override val viewModel: ReadingLessonViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.fragment_reading_lesson
    override val viewModelId: Int
        get() = BR.readingLessonViewModel

    override fun initView() {
        super.initView()
        initContent()
        initListener()
    }

    private fun initContent() {
        viewModel.lessonBean?.let {
            binding.imageIv.setCenterImage(it)
            binding.topContainer.titleTv.text = it.text
        }
    }

    private fun initListener() {
        binding.recordingIv.setOnClickListener(NonDoubleClickListener {
//            RecordManager.getInstance().let {
//                val path = viewModel.lessonBean?.record?.getFullPath()
//                    ?: return@NonDoubleClickListener
//                if (it.recordingState == RecordManager.RecordingState.RECORDING) {
//                    it.stopRecord()
//                } else {
//                    startRecordingWithPermissionCheck(path)
//                }
//            }
            TAIOralManager.oral.let {
                if (it.isRecording) {
                    it.stopRecord()
                } else {
                    val path = viewModel.lessonBean?.record?.getFullPath() ?: ""
                    startRecordingWithPermissionCheck(path)
                }
            }
        })
        binding.topContainer.audioIv.setOnClickListener(NonDoubleClickListener {
            playAudio()
        })
    }

    override fun initViewObserver() {
    }


    @NeedsPermission(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun startRecording(path: String) {
        try {
            File(path).let {
                if (it.exists()) {
                    it.delete()
                }
            }
        } catch (e: Exception) {
        }
        val target = binding.topContainer.titleTv.text.toString()
        TAIOralManager.oral.startRecord(requireContext(), target) { score, matcher ->
            handler.post {
                finishParsing(matcher, BusinessUtil.getStartsByScore(score))
            }
        }
//        RecordManager.getInstance().startMatcherRecord(path, target) {
//            handler.post {
//                finishParsing(it)
//            }
//        }
    }

    /**
     * 解析结果
     */
    private fun finishParsing(matcher: PhraseMatcher, star: Int? = null) {
        val endText = "Great!"
        binding.resultTv.visibility = View.VISIBLE
        binding.resultTv.text = endText
        BusinessUtil.refreshResult(
            target = binding.resultLl,
            stars = star ?: 3 - matcher.targetErrorIndexes.size,
            width = DeviceUtils.toPx(requireContext(), 34.0),
            offsetIndex = 1
        )
        val errors = arrayListOf<String>()
        val results =
            matcher.targets.filterIndexed { index, s -> matcher.targetErrorIndexes.contains(index) }
        errors.addAll(results)
        refreshTitle(errors)
        viewModel.done.value = true
    }

    /**
     * 刷新单词对错
     */
    private fun refreshTitle(errors: ArrayList<String>) {
        //
        val title = viewModel.lessonBean?.text ?: ""
        val spannableString = SpannableString(title)

        val errorSpan = ForegroundColorSpan(
            ContextCompat.getColor(
                requireContext(),
                R.color.hex_FF5337
            )
        );
        val baseSpan = ForegroundColorSpan(
            ContextCompat.getColor(
                requireContext(),
                R.color.hex_464646
            )
        );
        spannableString.setSpan(baseSpan, 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        errors.forEach {
            val start = title.indexOf(it)
            if (-1 != start) {
                spannableString.setSpan(
                    errorSpan,
                    start,
                    start + it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        binding.topContainer.titleTv.text = spannableString
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
