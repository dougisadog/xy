package com.shuange.lesson.modules.video.view

import android.content.Context
import android.content.Intent
import android.widget.MediaController
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityVideoGalleryBinding
import com.shuange.lesson.modules.video.adapter.VideoGalleryAdapter
import com.shuange.lesson.modules.video.viewmodel.VideoGalleryViewModel


/**
 * tiktok
 */
class VideoGalleryActivity :
    BaseActivity<ActivityVideoGalleryBinding, VideoGalleryViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, VideoGalleryActivity::class.java)
            context.startActivity(i)
        }

        var media: MediaController? = null
    }

    override val viewModel: VideoGalleryViewModel by viewModels {
        BaseShareModelFactory()

    }

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
            layoutManager = LinearLayoutManager(
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
                        (recyclerView.layoutManager as LinearLayoutManager).let {
                            val offset = recyclerView.computeVerticalScrollOffset()
                            val itemHeight = recyclerView.getChildAt(0)?.height ?: return
                            var index = offset / itemHeight
                            val lastIndex = recyclerView.tag as? Int
                            if (offset % itemHeight == 0) {
                                if (lastIndex != index) {
                                    (recyclerView.getChildAt(0)
                                        .findViewById(R.id.video) as? StyledPlayerView)?.let {
                                        it.player?.play()
                                    }
                                    recyclerView.tag = index
                                }
                            } else {
                                if (offset % itemHeight > itemHeight / 2) {
                                    index++
                                }
                                recyclerView.smoothScrollToPosition(index)
                            }
                        }
                    }
                }
            }
        })
    }


    override fun initViewObserver() {
    }

}