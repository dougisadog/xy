package com.shuange.lesson.service.api.base

import com.google.gson.Gson
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.service.response.base.SuspendResponse
import corelib.http.HttpTask
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume


abstract class BaseApi<DataType : Any> : HttpTask<DataType>() {

    override val baseURL: String = ConfigDef.LESSON_BASE_URL

    override fun parseResponse(data: String): DataType {
        val result = Gson().fromJson(data, resultClass.java)
        return result
    }
}

suspend fun <DataType : Any> HttpTask<DataType>.suspendExecute(): SuspendResponse<DataType> {
    val encoding = requestEncoding
    return suspendCancellableCoroutine { coroutine ->
        urlSessionTask = getHttpCall()
        urlSessionTask?.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val data: ByteArray?
                val result: DataType?
                try {
                    data = response.body?.bytes()
                    result = data?.let { parseResponse(String(it, encoding)) }
                } catch (e: IOException) {
                    coroutine.resume(
                        SuspendResponse(
                            exception = e
                        )
                    )
                    return
                }
                coroutine.resume(
                    SuspendResponse(
                        data = result
                    )
                )
            }

            override fun onFailure(call: Call, e: IOException) {
                coroutine.resume(
                    SuspendResponse(
                        exception = e
                    )
                )
            }
        })
        coroutine.invokeOnCancellation {
            cancel()
        }
    }
}