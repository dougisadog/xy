package com.shuange.lesson.utils

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.utils.AudioUtil.AUDIO_FREQUENCY
import com.shuange.lesson.utils.AudioUtil.generateWavFileHeader
import corelib.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.RandomAccessFile


class RecordManager private constructor() {

    enum class RecordingState {
        START, RECORDING, FINISHED, ERROR
    }

    companion object {
        private var mInstance: RecordManager? = null
            get() {
                if (field == null) {
                    field = RecordManager()
                }
                return field
            }

        @Synchronized
        fun getInstance(): RecordManager {
            return requireNotNull(mInstance)
        }

    }

    var recordingState = RecordingState.START

    private val minBuffer = AudioRecord.getMinBufferSize(
        AUDIO_FREQUENCY,
        AudioFormat.CHANNEL_IN_STEREO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    private var audioRecord = getAudioRecord()

    private fun getAudioRecord(): AudioRecord {
        return AudioRecord(
            MediaRecorder.AudioSource.MIC,
            AUDIO_FREQUENCY,
            AudioFormat.CHANNEL_IN_STEREO,
            AudioFormat.ENCODING_PCM_16BIT,
            minBuffer
        )
    }

    private fun generateAudioRecord() {
        if (audioRecord.state != AudioRecord.STATE_INITIALIZED) {
            audioRecord = getAudioRecord()
        }
    }

    fun stopRecord() {
        recordingState = RecordingState.FINISHED
        audioRecord.stop()
        audioRecord.release()
    }

    fun startRecord(filePath: String, recordDone: EmptyTask) {
        when (recordingState) {
            RecordingState.START, RecordingState.FINISHED, RecordingState.ERROR -> {
                recordingState = RecordingState.RECORDING
                Thread {
                    val f: File
                    try {
                        f = File(filePath)
                        if (f.exists()) {
                            f.delete()
                        }
                        f.parentFile?.let {
                            if (!it.exists()) {
                                it.mkdirs()
                            }
                        }
                        f.createNewFile()
                    } catch (e: Exception) {
                        Log.e("recording", "file create error ${e.message}")
                        RecordingState.ERROR
                        return@Thread
                    }
                    val fos = FileOutputStream(f)
                    val audioData = ByteArray(minBuffer)
                    generateAudioRecord()
                    audioRecord.startRecording()
                    try {
                        AudioUtil.writeWavFileHeader(
                            fos,
                            minBuffer.toLong(),
                            AUDIO_FREQUENCY.toLong(),
                            audioRecord.channelCount
                        )
                        val headerLength = f.length()
                        while (recordingState == RecordingState.RECORDING) {
                            val readSize = audioRecord.read(audioData, 0, minBuffer)
                            if (AudioRecord.ERROR_INVALID_OPERATION != readSize) {
                                fos.write(audioData)
                                fos.flush()
                            }
                        }
                        audioRecord.stop()
                        val wavRaf = RandomAccessFile(f, "rw")
                        val header = generateWavFileHeader(
                            f.length() - headerLength,
                            AUDIO_FREQUENCY.toLong(),
                            audioRecord.channelCount
                        )
                        wavRaf.seek(0)
                        wavRaf.write(header)
                        wavRaf.close()
                    } catch (e: Exception) {
                        Log.e("recording", "recording failed ${e.message}")
                        recordingState = RecordingState.ERROR

                    } finally {
                        try {
                            fos.close()
                            audioRecord.release()
                        } catch (e: Exception) {
                            recordingState = RecordingState.ERROR
                            Log.e("recording", "file close failed ${e.message}")
                        }
                    }
                    recordDone?.invoke()
                    recordingState = RecordingState.FINISHED
                }.start()
            }
            else -> {
                return
            }
        }
    }

}
