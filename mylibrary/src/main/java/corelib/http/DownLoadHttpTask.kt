package corelib.http

import android.text.TextUtils
import android.util.Log
import corelib.http.util.DownloadUtil

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Dispatcher
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.zip.GZIPInputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

abstract class DownLoadHttpTask : HttpTask<Long>() {

    var listener: DownloadProgressListener? = null

    private val suffix = ".temp"

    abstract var fileName: String

    abstract var savePath: String

    final override val resultClass = Long::class

    fun download() {
        if (state == TaskState.RUNNING) {
            return
        }
        state = TaskState.RUNNING
        getHttpCall().enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val result = parseResponse(response)
                successHandler?.invoke(result)
                finishHandler?.invoke()
            }

            override fun onFailure(call: Call, e: IOException) {
                errorHandler?.invoke(null, ResponseInfo())
                finishHandler?.invoke()
            }
        })
    }

    fun parseResponse(response: Response?): Long {
        var result: Long = 0
        if (response?.body != null) {
            val fileSize = response.body!!.contentLength()
            if (fileSize <= 0) {
                Log.d("DownLoadHttpTask", "Response doesn't present Content-Length!")
            }

            if (!savePath.endsWith(File.separator)) {
                savePath += File.separator
            }
            val mStoreFile = File(savePath + fileName)
            if (mStoreFile.exists()) {
                if (mStoreFile.length() == fileSize) {
                    if (null != listener) {
                        listener!!.onDownloading(100.toDouble(), fileSize, fileSize)
                    }
                    return fileSize
                } else {
                    mStoreFile.delete()
                }
            }
            val mTemporaryFile = File(savePath + fileName + suffix)

            try {
                if (!mTemporaryFile.exists()) {
                    mTemporaryFile.parentFile?.let {
                        if (!it.exists()) {
                            it.mkdirs()
                        }
                    }
                    mTemporaryFile.createNewFile()
                }
            } catch (e: Exception) {
                errorHandler?.invoke(0, ResponseInfo())
                return result
            }
            var downloadedSize = mTemporaryFile.length()
            val isSupportRange =
                DownloadUtil.isSupportRange(response)
            if (isSupportRange) {
                val realRangeValue = response.header("Content-Range")
                if (!TextUtils.isEmpty(realRangeValue)) {
                    val assumeRangeValue = ("bytes " + downloadedSize + "-"
                            + (fileSize - 1))
                    if (TextUtils.indexOf(realRangeValue, assumeRangeValue) == -1) {
                        throw IllegalStateException(
                            "The Content-Range Header is invalid Assume["
                                    + assumeRangeValue + "] vs Real["
                                    + realRangeValue + "], "
                                    + "please remove the temporary file ["
                                    + mTemporaryFile + "]."
                        )
                    }
                }
            }

            val tmpFileRaf = RandomAccessFile(mTemporaryFile, "rw")
            if (isSupportRange) {
                tmpFileRaf.seek(downloadedSize)
            } else {
                tmpFileRaf.setLength(0)
                downloadedSize = 0
            }

            var inStream = response.body!!.byteStream()
            try {
                if ((DownloadUtil.isGzipContent(response) && inStream !is GZIPInputStream)) {
                    inStream = GZIPInputStream(inStream)
                }
                inStream.skip(downloadedSize)
                val buffer = ByteArray(6 * 1024) // 6K buffer
                while (true) {
                    val offset = inStream.read(buffer)
                    if (offset <= 0)
                        break
                    tmpFileRaf.write(buffer, 0, offset)
                    downloadedSize += offset.toLong()
                    if (null != listener) {
                        val percent = BigDecimal(downloadedSize.toDouble() / fileSize)
                        percent.setScale(2, RoundingMode.HALF_UP)
                        listener!!.onDownloading(percent.toDouble(), downloadedSize, fileSize)
                    }
                    if (state == TaskState.CANCEL) {
                        break
                    }
                }
            } catch (e: Exception) {
                errorHandler?.invoke(0, ResponseInfo())
                state = TaskState.ERROR
                return result
            } finally {
                if (fileSize == downloadedSize) {
                    mTemporaryFile.renameTo(mStoreFile)
                    if (null != listener) {
                        listener!!.onDownloading(100.toDouble(), fileSize, fileSize)
                    }
                }
                result = downloadedSize
                inStream.close()
                try {
                    response.body!!.byteStream().close()
                } catch (e: Exception) {
                    Log.d("DownLoadHttpTask", "Error occured when calling consumingContent")
                }
                tmpFileRaf.close()
                state = TaskState.SUCCESS
            }
        }
        return result
    }

}

/**
 * Download callback
 */
interface DownloadProgressListener {
    fun onDownloading(downloadedPercent: Double, downloaded: Long, full: Long)
}
