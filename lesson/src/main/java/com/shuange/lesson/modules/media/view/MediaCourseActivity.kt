package com.shuange.lesson.modules.media.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityVideoCourseBinding
import com.shuange.lesson.modules.media.viewmodel.VideoCourseViewModel
import com.shuange.lesson.view.NonDoubleClickListener


/**
 *  视频课程
 */
class MediaCourseActivity : BaseActivity<ActivityVideoCourseBinding, VideoCourseViewModel>() {

    companion object {
        fun start(url: String, context: Context) {
            val i = Intent(context, MediaCourseActivity::class.java)
            i.putExtra(IntentKey.VIDEO_LINK_KEY, url)
            context.startActivity(i)
        }
    }

    override val viewModel: VideoCourseViewModel by viewModels {
        BaseShareModelFactory()
    }


    override val layoutId: Int
        get() = R.layout.activity_video_course
    override val viewModelId: Int
        get() = BR.videoCourseViewModel


    override fun initParams() {
        super.initParams()
        intent.getStringExtra(IntentKey.VIDEO_LINK_KEY).let {
            if (it.isNullOrEmpty()) {
//                finish()
//                return
            }
        }
    }

    override fun initView() {
        viewModel.loadData()
        initVideoView()
        initListeners()
        initDraggingCourses()
    }

    fun initDraggingCourses() {
        DraggingCourseFragment(viewModel.courses).show(supportFragmentManager, "dialog")
    }

    private fun initVideoView() {
        val filePath = viewModel.mediaData?.source?.url ?: return
//        val file = File(filePath)
//        if (!file.exists()) {
//            finish()
//            return
//        }
        val player = SimpleExoPlayer.Builder(this).build()
        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(filePath)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        binding.videoVv.player = player

    }

    private fun initListeners() {
        binding.buyTv.setOnClickListener(NonDoubleClickListener {

        })
        binding.backIv.setOnClickListener {
            finish()
        }
    }


    override fun initViewObserver() {
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoVv.player?.release()
    }

}