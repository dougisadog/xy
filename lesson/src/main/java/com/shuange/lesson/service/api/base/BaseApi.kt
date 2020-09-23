package com.shuange.lesson.service.api.base

import com.google.gson.Gson
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.service.api.base.exception.TokenErrorException
import com.shuange.lesson.service.response.base.SuspendResponse
import corelib.http.ErrorHandlingStatus
import corelib.http.HttpTask
import corelib.http.HttpTaskError
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.resume


abstract class BaseApi<DataType : Any> : HttpTask<DataType>() {

    override val baseURL: String = ConfigDef.LESSON_BASE_URL

    override fun parseResponse(data: String): DataType {
        val result = Gson().fromJson(data, resultClass.java)
        return result
    }
}

suspend fun <DataType : Any> HttpTask<DataType>.suspendExecute(): SuspendResponse<DataType> {
    return suspendCancellableCoroutine { coroutine ->
        this.execute {
            coroutine.resume(
                SuspendResponse(it)
            )
        }
        onError { dataType, responseInfo ->
            if (responseInfo.type == HttpTaskError.StatusCode) {
                coroutine.resume(
                    SuspendResponse(TokenErrorException(""))
                )
            }
            coroutine.resume(
                SuspendResponse(responseInfo.error ?: Exception(""))
            )
            ErrorHandlingStatus.CONTINUING
        }
        coroutine.invokeOnCancellation {
            cancel()
        }
    }
}