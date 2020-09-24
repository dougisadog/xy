package com.shuange.lesson.modules.video.view

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
import com.shuange.lesson.databinding.ActivityVideoBinding
import com.shuange.lesson.modules.video.viewmodel.VideoViewModel
import java.io.File


/**
 * videoView 视频播放
 */
class VideoActivity : BaseActivity<ActivityVideoBinding, VideoViewModel>() {

    companion object {
        fun startVideo(url: String, context: Context) {
            val i = Intent(context, VideoActivity::class.java)
            i.putExtra(IntentKey.VIDEO_LINK_KEY, url)
            context.startActivity(i)
        }
    }

    override val viewModel: VideoViewModel by viewModels {
        BaseShareModelFactory()
    }


    override val layoutId: Int
        get() = R.layout.activity_video
    override val viewModelId: Int
        get() = BR.videoViewModel


    override fun initParams() {
        super.initParams()
        intent.getStringExtra(IntentKey.VIDEO_LINK_KEY).let {
            if (it.isNullOrEmpty()) {
                finish()
                return
            }
            viewModel.url = it
        }
    }

    override fun initView() {
        initVideoView()
    }

    private fun initVideoView() {
        val filePath = viewModel.url ?: return
        val file = File(filePath)
        if (!file.exists()) {
            finish()
            return
        }
        val player = SimpleExoPlayer.Builder(this).build()
        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(filePath)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        binding.videoVv.player = player

    }


    override fun initViewObserver() {
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoVv.player?.release()
    }

}