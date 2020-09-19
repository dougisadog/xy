package com.shuange.lesson.utils

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.text.TextUtils
import corelib.util.Log
import java.io.IOException


class MediaPlayerMgr private constructor() {

    /** */
    /**
     * / **  MediaPlayer
     * / **
     * / */
    private var mp: MediaPlayer? = null
    private var path: String? = null
    private var callback: OnCompletionListener? = null
    private fun initMp() {}

    fun destroyMp() {
        if (null == mp) {
            return
        }
        mp?.let {
            stopMp()
            it.reset()
            it.release()
            mp = null
        }

    }

    fun playMp(path: String?) {
        playMp(path, null)
    }

    fun playMp(path: String?, callback: OnCompletionListener?) {
        if (TextUtils.isEmpty(path)) {
            return
        }
        stopMp()
        this.callback = callback
        this.path = path
        if (null == mp) {
            mp = MediaPlayer()
            mp!!.setOnPreparedListener(onPreparedListener)
            mp!!.setOnErrorListener(onErrorListener)
        } else {
            mp!!.reset()
        }
        try {
            mp!!.setOnCompletionListener(onCompletionListener)
            mp!!.setDataSource(this.path)
        } catch (e: IOException) {
            Log.e(TAG, e.message.toString())
        } catch (e: java.lang.IllegalStateException) {
            Log.e(TAG, e.message.toString())
        }
        prepare()
    }

    /**
     * 获取音频时长
     * @param path
     * @return
     */
    fun getDuration(path: String?, callback: OnGetDurationListener) {
        val mp = MediaPlayer()
        mp.setOnPreparedListener { mp ->
            val duration = mp.duration
            mp.reset()
            mp.release()
            callback.onGetDuration(duration)
        }
        try {
            mp.setDataSource(this.path)
            mp.prepareAsync()
        } catch (e: IOException) {
            Log.e(TAG, e.message.toString())
        } catch (e: IllegalStateException) {
            Log.e(TAG, e.message.toString())
        }
    }

    private val onPreparedListener =
        OnPreparedListener { mp ->
            if (mp === this@MediaPlayerMgr.mp) {
                mp.start()
            }
        }
    private val onPreparedListener2 =
        OnPreparedListener { mp -> mp.start() }
    private val onErrorListener: MediaPlayer.OnErrorListener =
        MediaPlayer.OnErrorListener { mp, what, extra ->
            mp.reset()
            false
        }
    private val onCompletionListener: OnCompletionListener =
        OnCompletionListener { if (null != callback) callback!!.onCompletion(mp) }


    interface OnGetDurationListener {
        fun onGetDuration(duration: Int)
    }

    private fun prepare() {
        try {
            mp?.prepareAsync()
        } catch (e: IllegalStateException) {
            onCompletionListener.onCompletion(mp)
            Log.e(TAG, e.message.toString())
        }
    }

    fun stopMp() {
        try {
            mp?.let {
                if (it.isPlaying) {
                    it.setOnCompletionListener(null)
                    it.stop()
                }
            }
        } catch (e: IllegalStateException) {
        }
    }

    companion object {
        const val TAG = " MediaPlayer"

        private var mInstance: MediaPlayerMgr? = null
            get() {
                if (null == field) {
                    field = MediaPlayerMgr()
                }
                return field
            }

        @JvmStatic
        @Synchronized//添加synchronized同步锁
        fun getInstance(): MediaPlayerMgr {
            return requireNotNull(mInstance)
        }
    }

    init {
        initMp()
    }
}
