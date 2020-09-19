package com.shuange.lesson.modules.lesson.view

import android.media.MediaMetadataRetriever
import androidx.fragment.app.viewModels
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.FragmentVideoLessonBinding
import com.shuange.lesson.modules.lesson.viewmodel.VideoLessonViewModel
import com.shuange.lesson.modules.video.view.VideoActivity
import com.shuange.lesson.utils.VideoUtil
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_title.view.*
import java.math.BigDecimal


class VideoLessonFragment :
    BaseLessonFragment<FragmentVideoLessonBinding, VideoLessonViewModel>() {

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
            setVideoThumbnail(it.video?.getFullPath() ?: "")
            binding.topContainer.titleTv.text = it.text
        }
    }

    private fun initListener() {
        binding.topContainer.audioIv.setOnClickListener(NonDoubleClickListener {
            playAudio()
        })
        binding.imageIv.setOnClickListener(NonDoubleClickListener {
            viewModel.lessonBean?.video?.getFullPath()?.let {
//                VideoWebActivity.startVideoWeb(it, requireContext())
                VideoActivity.startVideo(it, requireContext())
            }
        })
    }

    override fun initViewObserver() {
    }

    private fun setVideoThumbnail(filePath: String) {
        val bitmap = VideoUtil.getVideoThumbnail(filePath) ?: return
        binding.imageIv.setImageBitmap(bitmap)
    }

    override val viewModel: VideoLessonViewModel by viewModels {
        BaseShareModelFactory()
    }
    override val layoutId: Int
        get() = R.layout.fragment_video_lesson
    override val viewModelId: Int
        get() = BR.videoLessonViewModel

}