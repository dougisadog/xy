package com.shuange.lesson.modules.video.view

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Handler
import android.view.View
import android.widget.MediaController
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityVideoGalleryBinding
import com.shuange.lesson.jzvd.JzvdStdTikTok
import com.shuange.lesson.modules.video.adapter.VideoGalleryAdapter
import com.shuange.lesson.modules.video.viewmodel.VideoGalleryViewModel
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.VideoUtil


/**
 * tiktok
 */
class VideoGalleryActivity :
    BaseActivity<ActivityVideoGalleryBinding, VideoGalleryViewModel>() {

    companion object {
        fun startVideoGallery(context: Context) {
            val i = Intent(context, VideoGalleryActivity::class.java)
            context.startActivity(i)
        }

        var media: MediaController? = null
    }

    override val viewModel: VideoGalleryViewModel by viewModels {
        BaseShareModelFactory()

    }

    var handler = Handler()

    override val layoutId: Int
        get() = R.layout.activity_video_gallery
    override val viewModelId: Int
        get() = BR.videoGalleryViewModel


    private val videoGalleryAdapter: VideoGalleryAdapter by lazy {
        VideoGalleryAdapter(
            data = viewModel.videoData
        )
    }

    override fun initView() {
        media = MediaController(this).apply {
            setPadding(0, 0, 0, 100)
        }
        viewModel.loadData()
        initVideos()
        initListener()
    }

    fun initVideos() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@VideoGalleryActivity,
                RecyclerView.VERTICAL,
                false
            )
            videoGalleryAdapter.heartClick = { videoData, selected ->
                viewModel.hit(videoData.id)
            }
            adapter = videoGalleryAdapter
        }
    }

    var firstVisibleItem: Int? = null
    var lastVisibleItem: Int? = null

    fun initListener() {
        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {


                    }
                    /**在这里执行，视频的自动播放与停止 */
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        autoPlayVideo(recyclerView)
                    }
//                    RecyclerView.SCROLL_STATE_SETTLING -> Jzvd.releaseAllVideos()
                }
            }
        })
    }

    /**
     * 自动播放
     */
    private fun autoPlayVideo(recyclerView: RecyclerView?) {
        val firstVisibleItem = firstVisibleItem ?: return
        val lastVisibleItem = lastVisibleItem ?: return

        for (i in 0..lastVisibleItem) {
            var current: View? = null
            try {
                current = recyclerView?.getChildAt(i)
            } catch (e: Exception) {
            }
            if (null == current) {
                return
            }
            val videoView: JzvdStdTikTok? = current.findViewById(R.id.video)
            if (videoView != null) {
                val rect = Rect()
                //获取视图本身的可见坐标，把值传入到rect对象中
                videoView.getLocalVisibleRect(rect)
                //获取视频的高度
                val videoHeight: Int = videoView.height
                if (rect.top <= 100 && rect.bottom >= videoHeight) {
                    startTask = Runnable {
                        if (videoView.state != Jzvd.STATE_PLAYING) {
                            videoView.startVideo()
                        }
//                        videoView.setMediaController(media)
//                        videoView.start()
                    }
                    handler.postDelayed(startTask!!, VideoUtil.AUTO_PLAY_DELAY)
                    return
                } else {
                    if (videoView.state == Jzvd.STATE_PLAYING) {
                        videoView.mediaInterface?.pause()
                        videoView.onStatePause()
                    }
//                    videoView.pause()
                }
            }
        }
    }

    var startTask: Runnable? = null


    override fun initViewObserver() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaController = null
        handler.removeCallbacksAndMessages(null)
    }
}