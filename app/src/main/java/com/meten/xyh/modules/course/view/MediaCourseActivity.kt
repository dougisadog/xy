package com.meten.xyh.modules.course.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityVideoCourseBinding
import com.meten.xyh.modules.course.viewmodel.VideoCourseViewModel
import com.meten.xyh.modules.recharge.view.RechargeActivity
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.media.view.DraggingCourseFragment
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.view.dialog.CommonDialog


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
        DraggingCourseFragment(viewModel.courses) {


        }.show(supportFragmentManager, "dialog")
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
        viewModel.valueNotEnough.observe(this , Observer {
            CommonDialog(this).apply {
                contentText = "确定花费100希氧币购买次课程吗？"
                cancelButtonText = "残忍拒绝"
                confirmButtonText = "去充值"
                onClick = {
                    RechargeActivity.start(this@MediaCourseActivity)
                }
            }.show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoVv.player?.release()
    }

}