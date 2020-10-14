package com.shuange.lesson.modules.course.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.base.config.IntentKey
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityVideoCourseBinding
import com.shuange.lesson.modules.course.viewmodel.VideoCourseViewModel
import com.shuange.lesson.modules.topquality.bean.CourseBean
import com.shuange.lesson.utils.ActivityUtil
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.view.dialog.CommonDialog


/**
 *  视频课程
 */
class MediaCourseActivity : BaseActivity<ActivityVideoCourseBinding, VideoCourseViewModel>() {

    companion object {
        fun start(bean: CourseBean, context: Context) {
            val i = Intent(context, MediaCourseActivity::class.java)
            i.putExtra(IntentKey.LESSON_PACKAGE_ITEM, bean)
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

    var draggingCourseFragment: DraggingCourseFragment? = null


    override fun initParams() {
        super.initParams()
        viewModel.courseBean =
            intent?.getSerializableExtra(IntentKey.LESSON_PACKAGE_ITEM) as? CourseBean
                ?: return
    }

    override fun initView() {
        viewModel.loadData()
        initListeners()
        switchDraggingCourses()
    }

    private fun switchDraggingCourses() {
        if (null == draggingCourseFragment) {
            draggingCourseFragment = DraggingCourseFragment(viewModel.draggingCourses)
        }
        val f = draggingCourseFragment ?: return
        if (f.isAdded) {
            f.dismiss()
        } else {
            f.show(supportFragmentManager, "dialog")
        }
    }

    private fun resetVideo() {
        val filePath = viewModel.mediaData.value?.source?.url ?: return
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
            viewModel.buyCourse.value = viewModel.draggingCourses.firstOrNull()?.id ?: "0"
        })
        binding.backIv.setOnClickListener {
            finish()
        }
        binding.listIv.setOnClickListener(NonDoubleClickListener {
            switchDraggingCourses()
        })
    }


    override fun initViewObserver() {
        viewModel.valueNotEnough.observe(this, Observer {
            showRechargeDialog()
        })
        viewModel.mediaData.observe(this, Observer {
            binding.videoVv.player?.release()
            resetVideo()
        })
        viewModel.buyCourse.observe(this, Observer {
            val price = viewModel.courseBean?.price ?: return@Observer
            showPurchaseDialog(price)
        })
        viewModel.courseRefresh.observe(this, Observer {

        })
    }

    fun showPurchaseDialog(price: Int) {
        CommonDialog(this).apply {
            contentText = "确定花费${price}希氧币购买次课程吗？"
            cancelButtonText = "残忍拒绝"
            confirmButtonText = "确认购买"
            onClick = {
                viewModel.buyCourse()
            }
        }.show()
    }

    fun showRechargeDialog() {
        CommonDialog(this).apply {
            contentText = "您的希氧币不足，确定去充值吗？"
            cancelButtonText = "残忍拒绝"
            confirmButtonText = "去充值"
            onClick = {
                ActivityUtil.startOutsideActivity(
                    this@MediaCourseActivity,
                    ConfigDef.RECHARGE_CLASS
                )
            }
        }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoVv.player?.release()
    }

    override fun onPause() {
        super.onPause()
        binding.videoVv.player?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.videoVv.player?.let {
            if (!it.isPlaying) {
                it.play()
            }
        }
    }
}