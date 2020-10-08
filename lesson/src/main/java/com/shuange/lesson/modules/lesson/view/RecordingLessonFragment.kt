package com.shuange.lesson.modules.lesson.view

import android.Manifest
import android.annotation.SuppressLint
import android.os.Handler
import android.view.MotionEvent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentRecordingLessonBinding
import com.shuange.lesson.modules.lesson.viewmodel.RecordingLessonViewModel
import com.shuange.lesson.utils.MediaPlayerMgr
import com.shuange.lesson.utils.RecordManager
import com.shuange.lesson.utils.extension.setCenterImage
import com.shuange.lesson.view.NonDoubleClickListener
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
open class RecordingLessonFragment :
    BaseLessonFragment<FragmentRecordingLessonBinding, RecordingLessonViewModel>() {

    val handler by lazy { Handler() }

    override val viewModel: RecordingLessonViewModel by viewModels {
        BaseShareModelFactory()
    }

    override fun initView() {
        super.initView()
        initContent()
        initListener()
    }

    private fun initContent() {
        viewModel.questionBean?.let {
            binding.imageIv.setCenterImage(it)
            binding.titleTv.text = it.text
        }
        binding.resetIv.isEnabled = false
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {
        val path = viewModel.questionBean?.record?.getFullPath() ?: return
        binding.recordingIv.setOnTouchListener { v, event ->
            RecordManager.getInstance().let {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    if (it.recordingState != RecordManager.RecordingState.RECORDING) {
                        startRecordingWithPermissionCheck(path)
                    }
                }
                if (event.action == MotionEvent.ACTION_UP) {
                    it.stopRecord()
                }
                true
            }
        }
        binding.resetIv.setOnClickListener(NonDoubleClickListener {
            MediaPlayerMgr.getInstance().playMp(path)
        })
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            //TODO
            next()
        })
    }


    override fun initViewObserver() {
        viewModel.recordDone.observe(this, Observer {
            if (null == it) return@Observer
            binding.resetIv.isEnabled = it
        })
    }


    @NeedsPermission(Manifest.permission.RECORD_AUDIO)
    fun startRecording(path: String) {
        Glide.with(this).load(R.drawable.icon_recording_progress).into(binding.resetIv)
        RecordManager.getInstance().startRecord(path) {
            viewModel.recordDone.value = true
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_recording_lesson
    override val viewModelId: Int
        get() = BR.recordingLessonViewModel

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
