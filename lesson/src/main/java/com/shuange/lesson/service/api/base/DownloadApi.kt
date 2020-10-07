package com.shuange.lesson.service.api.base

import com.shuange.lesson.modules.lesson.bean.SourceData
import corelib.http.DownLoadHttpTask
import corelib.http.TaskState
import corelib.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class DownloadApi(
    var sourceData: SourceData
) : DownLoadHttpTask() {

    override var savePath: String = sourceData.getDic() ?: ""
    override var fileName: String = sourceData.name + "." + sourceData.suffix
    override val baseURL: String
        get() = sourceData.url
//        get() = "https://downloads.gradle.org/distributions/gradle-5.3-rc-2-src.zip"


    suspend fun suspendDownload(): Long {
        if (sourceData.url.isBlank()) {
            Log.e("DownloadApi", "not valid source ${sourceData.name}")
            return 0
        }
        if (state == TaskState.RUNNING) {
            return 0
        }
        state = TaskState.RUNNING
        return suspendCancellableCoroutine { block ->
            getHttpCall().enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    state = TaskState.ERROR
                    block.resumeWith(kotlin.runCatching {
                        0L
                    })
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        state = TaskState.SUCCESS
                        block.resumeWith(kotlin.runCatching {
                            parseResponse(response)
                        })
                    }
                }
            })
            block.invokeOnCancellation {
                cancel()
            }

        }
    }
}


//private fun startFileDownloadTask() {
//    val savePath = filesDir.absolutePath + File.separator + "download" + File.separator
//    val fileName = "gradle-5.3-rc-2-src.zip"
//    downloader = DownloadApi(savePath, fileName)
//    downloader!!.listener = object : DownloadProgressListener {
//        override fun onDownloading(downloadedPercent: Double, downloaded: Long, full: Long) {
//            downloadPercentProgress.progress = (downloadedPercent *downloadPercentProgress.max).toInt()
//        }
//    }
//    downloader!!.download()
//}