package com.shuange.lesson.modules.video.adapter

import androidx.databinding.ObservableArrayList
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.databinding.LayoutVideoGalleryItemBinding
import com.shuange.lesson.modules.video.bean.VideoData
import com.shuange.lesson.view.NonDoubleClickListener


class VideoGalleryAdapter(data: ObservableArrayList<VideoData>?) :
    BaseListAdapter<VideoData>(R.layout.layout_video_gallery_item, data) {

    var heartClick: ((VideoData, Boolean) -> Unit)? = null
    override fun convert(helper: ListViewHolder?, item: VideoData?) {

        val binding = helper?.binding as? LayoutVideoGalleryItemBinding ?: return
        item ?: return
        item.source?.url?.let {
            val player = SimpleExoPlayer.Builder(binding.root.context).build()
            // Build the media item.
            val mediaItem: MediaItem = MediaItem.fromUri(it)
            player.setMediaItem(mediaItem)
            player.prepare()
            binding.video.player = player;
        }
        binding.heartsIv.setOnClickListener(NonDoubleClickListener {
            val target = !binding.heartsIv.isSelected
            binding.heartsIv.isSelected = target
            heartClick?.invoke(item, target)
        })
        binding.setVariable(BR.videoData, item)
    }


    override fun onViewDetachedFromWindow(holder: ListViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val binding = holder.binding as? LayoutVideoGalleryItemBinding ?: return
        binding.video.player?.pause()


    }
}