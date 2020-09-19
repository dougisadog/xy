package corelib.http.util

import okhttp3.Response

object DownloadUtil {

    fun isGzipContent(response:Response):Boolean {
        return "gzip" == response.header("Content-Encoding")?.toLowerCase()
    }

    fun isSupportRange(response:Response):Boolean {
        return true
    }


}