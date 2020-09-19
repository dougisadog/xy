package com.shuange.lesson.modules.video.adapter

import androidx.databinding.ObservableArrayList
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.databinding.LayoutVideoGalleryItemBinding
import com.shuange.lesson.modules.video.bean.VideoData
import com.shuange.lesson.modules.video.view.VideoGalleryActivity
import com.shuange.lesson.utils.VideoUtil
import com.shuange.lesson.view.NonDoubleClickListener

class VideoGalleryAdapter(data: ObservableArrayList<VideoData>?) :
    BaseListAdapter<VideoData>(R.layout.layout_video_gallery_item, data) {

    var heartClick: ((VideoData, Boolean) -> Unit)? = null
    override fun convert(helper: ListViewHolder?, item: VideoData?) {

        val binding = helper?.binding as? LayoutVideoGalleryItemBinding ?: return
        item ?: return
        item.source?.url?.let {
            //封面
            Glide.with(binding.video.context).load(it)
                .into(binding.video.posterImageView)
            val jzDataSource = JZDataSource(
                it,
                ""
            )
            jzDataSource.looping = true
            binding.video.setUp(jzDataSource, Jzvd.SCREEN_NORMAL)
//            VideoUtil.prepare(binding.video, it, VideoGalleryActivity.media)
        }
        binding.heartsIv.setOnClickListener(NonDoubleClickListener {
            val target = !binding.heartsIv.isSelected
            binding.heartsIv.isSelected = target
            heartClick?.invoke(item, target)
        })
        binding.setVariable(BR.videoData, item)
    }

}