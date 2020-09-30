package com.shuange.lesson.service.api.base

import com.google.gson.Gson
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.service.api.base.exception.TokenErrorException
import com.shuange.lesson.service.response.base.SuspendResponse
import corelib.http.ErrorHandlingStatus
import corelib.http.HttpTask
import corelib.http.HttpTaskError
import corelib.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


abstract class BaseApi<DataType : Any> : HttpTask<DataType>() {

    override val baseURL: String = ConfigDef.LESSON_BASE_URL

    override fun parseResponse(data: String): DataType {
        Log.d("response", data)
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
            var exception = responseInfo.error ?: Exception("")
            if (responseInfo.type == HttpTaskError.StatusCode) {
                exception = TokenErrorException("status error code:${responseInfo.response?.code}")
            }
            coroutine.resume(
                SuspendResponse(exception)
            )
            ErrorHandlingStatus.CONTINUING
        }
        coroutine.invokeOnCancellation {
            cancel()
        }
    }
}