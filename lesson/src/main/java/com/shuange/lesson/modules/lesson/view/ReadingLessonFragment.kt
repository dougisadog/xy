package com.shuange.lesson.modules.lesson.view

import android.Manifest
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentReadingLessonBinding
import com.shuange.lesson.modules.lesson.viewmodel.ReadingLessonViewModel
import com.shuange.lesson.utils.Base64Util
import com.shuange.lesson.utils.BusinessUtil
import com.shuange.lesson.utils.PhraseMatcher
import com.shuange.lesson.utils.RecordManager
import com.shuange.lesson.utils.extension.setCenterImage
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.youdao.YoudaoParser
import com.youdao.sdk.app.Language
import com.youdao.sdk.common.Constants
import com.youdao.voicerecognize.online.*
import corelib.util.DeviceUtils
import kotlinx.android.synthetic.main.layout_title.view.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.PermissionUtils
import permissions.dispatcher.RuntimePermissions


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

    /**
     * 录音失败的提示
     */
//    var listener: ExtAudioRecorder.RecorderListener = object : RecorderListener() {
//        fun recordFailed(failRecorder: Int) {
//            if (failRecorder == 0) {
//                Toast.makeText(this@RecognizeDemoActivity, "录音失败，可能是没有给权限", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                Toast.makeText(this@RecognizeDemoActivity, "发生了未知错误", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun initListener() {
        binding.recordingIv.setOnClickListener(NonDoubleClickListener {
            RecordManager.getInstance().let {
                val path = viewModel.lessonBean?.record?.getFullPath()
                    ?: return@NonDoubleClickListener
                if (it.recordingState == RecordManager.RecordingState.RECORDING) {
                    it.stopRecord()
                } else {
                    startRecordingWithPermissionCheck(path)
                }
            }
        })
        binding.topContainer.audioIv.setOnClickListener(NonDoubleClickListener {
            playAudio()
        })
    }

    fun parseResult(bases64: String) {
        val target = binding.topContainer.titleTv.text.toString()
        YoudaoParser.parseResult(bases64, target) {
            handler.post {
                finishParsing(it)
            }
        }
    }

    override fun initViewObserver() {
    }


    @NeedsPermission(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun startRecording(path: String) {
        RecordManager.getInstance().startRecord(path) {
            val base64 =
                Base64Util.encodeFileToBase64Binary(path) ?: return@startRecord
            parseResult(base64)
        }
    }

    private fun finishParsing(matcher: PhraseMatcher) {
        val endText = "Great!"
        binding.resultTv.visibility = View.VISIBLE
        binding.resultTv.text = endText
        BusinessUtil.refreshResult(
            target = binding.resultLl,
            stars = 3 - matcher.targetErrorIndexes.size,
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
