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
import com.shuange.lesson.utils.extension.setCenterImage
import com.shuange.lesson.view.NonDoubleClickListener
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
        viewModel.questionBean?.let {
            binding.imageIv.setCenterImage(it)
            binding.topContainer.titleTv.text = it.text
        }
    }

    private fun initListener() {
        binding.recordingIv.setOnClickListener(NonDoubleClickListener {
            //recording
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
                    binding.recordingIv.visibility = View.INVISIBLE
                    binding.recordingLabel.visibility = View.INVISIBLE
                } else {
                    val path = viewModel.questionBean?.record?.getFullPath() ?: ""
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
        TAIOralManager.oral.startRecord(requireContext(), target) { score, words ->
            handler.post {
                val errors = words.filter { it.matchTag != 0 }.map { it.word }
                finishParsing(errors, BusinessUtil.getStartsByScore(score))
            }
        }
        //audio parser
//        RecordManager.getInstance().startRecord(path) {
//            val file = File(path)
//            if (file.exists()) {
//                TAIOralManager.oral.parser(requireContext(),target, file) { score, words->
//                     finishParsing(errors, BusinessUtil.getStartsByScore(score))
//                }
//            }
        //Youdao
//            val base64 =
//                Base64Util.encodeFileToBase64Binary(path)?:return@startRecord
//            YoudaoParser.parseResult(base64, target) {
//                matcher.invoke(it)
//            }
//        }
    }

    /**
     * 解析结果 by PhraseMatcher
     */
    private fun finishParsingByMatcher(matcher: PhraseMatcher, star: Int = 3 - matcher.targetErrorIndexes.size) {
        val errors = arrayListOf<String>()
        val results =
            matcher.targets.filterIndexed { index, s -> matcher.targetErrorIndexes.contains(index) }
        errors.addAll(results)
        finishParsing(errors, star)
    }

    /**
     * 解析结果
     */
    private fun finishParsing(errors: List<String>, star: Int) {
        val endText = "Great!"
        binding.resultIv.visibility = View.VISIBLE
        var source:Int = 0
        when (star) {
            0,1 -> {
                source = R.drawable.icon_try_again
            }
            2 -> {
                source = R.drawable.icon_great
            }
            3 -> {
                source = R.drawable.icon_perfect
            }
        }
        binding.resultIv.setImageResource(source)
        BusinessUtil.refreshResult(
            target = binding.resultLl,
            stars = star,
            width = DeviceUtils.toPx(requireContext(), 34.0),
            offsetIndex = 1
        )
        refreshTitle(errors)
        next(isDelay = true, answer = "", score = if (star >= 2) 100 else 0)
    }

    /**
     * 刷新单词对错
     */
    private fun refreshTitle(errors: List<String>) {
        //
        val title = viewModel.questionBean?.text ?: ""
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
        val titleText = title.toLowerCase()
        errors.forEach {
            val start = titleText.indexOf(it.toLowerCase())
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
