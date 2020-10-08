package com.shuange.lesson.service.api.base

import com.google.gson.Gson
import com.shuange.lesson.base.config.ConfigDef
import com.shuange.lesson.service.api.base.exception.LogicErrorResponse
import com.shuange.lesson.service.response.base.SuspendResponse
import corelib.http.ErrorHandlingStatus
import corelib.http.HttpTask
import corelib.http.HttpTaskError
import corelib.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import kotlin.coroutines.resume


abstract class BaseApi<DataType : Any> : HttpTask<DataType>() {

    override val baseURL: String = ConfigDef.LESSON_BASE_URL

    override fun parseResponse(data: String): DataType {
        Log.d("response", data)
        val result = Gson().fromJson(data, resultClass.java)
        return result
    }

    fun addPageParam(
        startId: String = "0",
        page: Int = 0,
        size: Int = ConfigDef.DEFAULT_PAGE_SIZE
    ) {
        addQuery("id.greaterThan", startId)
        addQuery("page", page)
        addQuery("size", size)
    }

    override fun handleError(type: HttpTaskError, result: DataType?, response: ResponseInfo) {
        //预处理400逻辑错误
        if (type == HttpTaskError.StatusCode && response.response?.code == 400) {
            var message = response.error?.message
            try {
                message = Gson().fromJson(message, LogicErrorResponse::class.java).message ?: ""
            } catch (e: Exception) {
            }
            response.error = IOException(message)
        }
        super.handleError(type, result, response)
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
            var exception = responseInfo.error ?: Exception("未知错误")
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