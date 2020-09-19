package com.shuange.lesson.utils

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.widget.MediaController
import android.widget.VideoView
import java.math.BigDecimal

object VideoUtil {

    const val AUTO_PLAY_DELAY = 500L

    /**
     * 获取预加载视频1/10 的关键帧
     */
    fun getVideoThumbnail(filePath: String): Bitmap? {

        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(filePath)
            val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            //视频1/10 的关键帧
            val bitmap = retriever.getFrameAtTime(BigDecimal(time).toInt() / 10 * 1000L)
            return bitmap.copy(Bitmap.Config.ARGB_8888, true)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        } finally {
            try {
                retriever.release()
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun prepare(video: VideoView, url: String, mediaController: MediaController?) {
        val mediaController = mediaController ?: MediaController(video.context)
        mediaController.setMediaPlayer(video)
        video.setMediaController(mediaController)
        video.setVideoPath(url) //设置视频文件
        video.setOnPreparedListener {
            //视频加载完成,准备好播放视频的回调
            video.start()
        }
        video.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            //视频播放完成后的回调
        })
    }
}