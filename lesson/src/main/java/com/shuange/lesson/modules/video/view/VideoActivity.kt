package com.shuange.lesson.modules.video.view

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.widget.MediaController
import androidx.activity.viewModels
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
        val mediaController = MediaController(this)
        mediaController.setMediaPlayer(binding.videoVv)
        binding.videoVv.setMediaController(mediaController)
        binding.videoVv.setVideoPath(file.absolutePath) //设置视频文件
        binding.videoVv.setOnPreparedListener(OnPreparedListener {
            //视频加载完成,准备好播放视频的回调
            binding.videoVv.start()
        })
        binding.videoVv.setOnCompletionListener(OnCompletionListener {
            //视频播放完成后的回调
        })
        binding.videoVv.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra ->
            //异常回调
            false //如果方法处理了错误，则为true；否则为false。返回false或根本没有OnErrorListener，将导致调用OnCompletionListener。
        })
        binding.videoVv.setOnInfoListener(MediaPlayer.OnInfoListener { mp, what, extra ->
            //信息回调
//                what 对应返回的值如下
//                public static final int MEDIA_INFO_UNKNOWN = 1;  媒体信息未知
//                public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700; 媒体信息视频跟踪滞后
//                public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3; 媒体信息\视频渲染\开始
//                public static final int MEDIA_INFO_BUFFERING_START = 701; 媒体信息缓冲启动
//                public static final int MEDIA_INFO_BUFFERING_END = 702; 媒体信息缓冲结束
//                public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703; 媒体信息网络带宽（703）
//                public static final int MEDIA_INFO_BAD_INTERLEAVING = 800; 媒体-信息-坏-交错
//                public static final int MEDIA_INFO_NOT_SEEKABLE = 801; 媒体信息找不到
//                public static final int MEDIA_INFO_METADATA_UPDATE = 802; 媒体信息元数据更新
//                public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901; 媒体信息不支持字幕
//                public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902; 媒体信息字幕超时
            false //如果方法处理了信息，则为true；如果没有，则为false。返回false或根本没有OnInfoListener，将导致丢弃该信息。
        })
    }


    override fun initViewObserver() {
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoVv.stopPlayback();//停止播放视频,并且释放
        binding.videoVv.suspend();//在任何状态下释放媒体播放器
    }

}