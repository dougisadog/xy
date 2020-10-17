package corelib.http

import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.gson.GsonBuilder
import corelib.TimeInterval
import corelib.VoidFunction
import corelib.extension.append
import corelib.extension.intTag
import corelib.extension.lowercased
import corelib.extension.removeFirst
import corelib.util.Log
import corelib.util.QueryUtils
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.net.SocketException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import kotlin.reflect.KClass


/**
 * HTTP通信処理基盤
 */
abstract class HttpTask<DataType : Any> : TaskStateListener {

    companion object {
        val httpClient = OkHttpClient()
        var debug = true
    }

    val logTag = "HttpTask"

    // アプリをフォアグラウンドにした際に実行する必要がある処理
    private var resumeProcessList: MutableList<VoidFunction> = mutableListOf()

    data class ResponseInfo(
        var type: HttpTaskError? = null,
        var data: ResponseBody? = null,
        var response: Response? = null,
        var error: IOException? = null
    )

    var indicator: View? = null
    var successHandler: ((DataType) -> Unit)? = null
    var errorHandler: ((DataType?, ResponseInfo) -> ErrorHandlingStatus)? = null
    var finishHandler: (() -> Unit)? = null
    var requestAgainHandler: (() -> Unit)? = null
    var queryItems: MutableList<Pair<String, Any>> = mutableListOf()
    val handler = Handler(Looper.getMainLooper())

    var urlSessionTask: Call? = null

    private var afterProcess: (() -> Unit)? = null
    var afterProcessDelaySec: Double? = null
    var invokeSuccessProcess: Runnable? = null

    /**
     * start sync task
     */
    fun syncExecute(): DataType? {
        state = TaskState.START
        indicator?.let { it.intTag += 1 }
        var result: DataType? = null
        try {
            val response = getHttpCall().execute()
            if (null == response.body)
                return result
            result = convert(response.body!!)
        } catch (e: Exception) {
            e.message?.let { Log.e(logTag, it) }
        } finally {
            updateIndicator()
        }
        return result
    }

    /**
     * start async task
     */
    fun execute(action: ((DataType) -> Unit)? = null): HttpTask<DataType> {
        state = TaskState.START
        successHandler = action
        startConnection()
        return this
    }

    /**
     * set error handler
     * @param action ((DataType?, ResponseInfo) -> ErrorHandlingStatus)?
     * @return HttpTask<DataType>
     */
    open fun onError(action: ((DataType?, ResponseInfo) -> ErrorHandlingStatus)? = null): HttpTask<DataType> {
        errorHandler = action
        return this
    }

    /**
     * set finish handler
     * @param action (() -> Unit)?
     */
    fun onFinish(action: (() -> Unit)?) {
        finishHandler = action
    }

    fun onRequestAgain(action: (() -> Unit)? = null): HttpTask<DataType> {
        requestAgainHandler = action
        return this
    }

    fun getHttpCall(): Call {

        prepareRequest()

        val requestBuilder = getRequestBuilder()

        val client = initOkHttpClientBuild()

        return client.build().newCall(requestBuilder.build())
    }

    /**
     * init jp.co.sbibits.base request
     * @return Request.Builder
     */
    private fun getRequestBuilder(): Request.Builder {
        var url = requestURL
        if (httpMethod == "GET") {
            val query = makeQueryString()
            if (null != query) {
                url += "?$query"
            }
        }
        // リクエスト作成
        val requestBuilder = Request.Builder()
            .url(url)
            .cacheControl(CacheControl.Builder().noCache().noStore().build())

        val headers = this.headers
        val userAgent = CommonUserAgent.value
        if (userAgent != null && !headers.keys.map { it.lowercased() }.contains("user-agent")) {
            requestBuilder.addHeader("User-Agent", userAgent)
        }
        //java.io.IOException: unexpected end of stream
        requestBuilder.addHeader("Connection", "close")
        for ((key, value) in headers) {
            Log.e("header", "$key : $value")
            requestBuilder.addHeader(key, value)
        }
        if (null != auth) {
            requestBuilder.addHeader(auth!!.first, auth!!.second)
        }

        return buildRequestBody(requestBuilder)
    }

    open fun buildRequestBody(requestBuilder: Request.Builder): Request.Builder {
        var body: RequestBody? = customizeBody()
        if (httpMethod != "GET") {
            if (null == body) {
                var contentType = "application/x-www-form-urlencoded;charset=$requestEncoding"
                when (postType) {
                    PostType.JSON -> {
                        contentType = "application/json;charset=$requestEncoding"
                        val mediaType = contentType.toMediaTypeOrNull()
                        val gson = GsonBuilder().enableComplexMapKeySerialization().create()
                        val jsonText = gson.toJson(queryItems.toMap())
                        body = RequestBody.create(mediaType, jsonText)
                    }
                    PostType.FORM -> {
                        val builder = FormBody.Builder()
                        queryItems.forEach {
                            builder.add(it.first, it.second.toString())
                        }
                        body = builder.build()
                    }
                }
                requestBuilder.header("Content-type", contentType)
            }
        }
        requestBuilder.method(httpMethod, body)
        return requestBuilder
    }

    /**
     * default builder
     * @return OkHttpClient.Builder
     */
    open fun initOkHttpClientBuild(): OkHttpClient.Builder {
        val builder = httpClient.newBuilder()
            .retryOnConnectionFailure(false)
            .connectTimeout(timeoutIntervalForRequest.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeoutIntervalForResource.toLong(), TimeUnit.SECONDS)
            .sslSocketFactory(
                SSLContext.getDefault().socketFactory,
                DefaultSetting.trustManager
            )
//        if (BuildConfig.DEBUG) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(logInterceptor)
//        }
        return builder
    }

    fun startConnection() {
        indicator?.let { it.intTag += 1 }
        urlSessionTask = getHttpCall()
        urlSessionTask?.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val data: ResponseBody?
                try {
                    data = response.body
                } catch (e: IOException) {
                    complete(null, null, e)
                    return
                }
                complete(data, response, null)
            }

            override fun onFailure(call: Call, e: IOException) {
                complete(null, null, e)
            }
        })

        updateIndicator()
    }

    private fun complete(data: ResponseBody?, response: Response?, error: IOException?) {
        try {
            val responseInfo = ResponseInfo()
            responseInfo.data = data
            responseInfo.error = error
            if (error != null) {
                if (!error.toString().endsWith("Canceled")) {
                    handleError(HttpTaskError.Network, result = null, response = responseInfo)
                }
                return
            }
            if (response == null) {
                handleError(HttpTaskError.Network, result = null, response = responseInfo)
                return
            }
            responseInfo.response = response
            if (!checkStatusCode(response.code)) {
                responseInfo.error = IOException(data?.string() ?: "")
                handleError(HttpTaskError.StatusCode, result = null, response = responseInfo)
                return
            }
            @Suppress("NAME_SHADOWING")
            val data = data
            if (data == null) {
                handleError(HttpTaskError.Network, result = null, response = responseInfo)
                return
            }

            val result: DataType
            try {
                response?.let {
                    parseResponseHeader(response)
                }
                result = convert(data)
            } catch (e: Exception) {
                e.printStackTrace()
                if (e !is SocketException) {
                    handleError(HttpTaskError.Parse, result = null, response = responseInfo)
                }
                return
            }
            if (isValidResponse(result)) {
                invokeSuccessProcess = Runnable {
                    preProcess(result)
                    successHandler?.invoke(result)
                    postProcess(result)
                    state = TaskState.SUCCESS
                }
                handler.post(invokeSuccessProcess!!)
            } else {
                handleError(HttpTaskError.InvalidResponse, result = result, response = responseInfo)
            }
        } finally {
            handler.post {
                indicator?.let { it.intTag -= 1 }
                updateIndicator()
                finishHandler?.invoke()

            }
        }
    }

    open fun parseResponseHeader(response: Response) {

    }

    open fun handleError(type: HttpTaskError, result: DataType?, response: ResponseInfo) {
        response.type = type
        handler.post {
            val message = response.error?.toString() ?: ""
//            if (response.error?.message != "Canceled") {
//                LogUtils.e(this::class.simpleName, "Connection Error: $type, $message", response.error)
//            }
            if (errorHandler == null || errorHandler?.invoke(
                    result,
                    response
                ) == ErrorHandlingStatus.CONTINUING
            ) {
                errorProcess(type, result = result, info = response)
            }
            state = TaskState.ERROR
        }
    }

    fun cancel() {
        if (null != urlSessionTask && !urlSessionTask!!.isCanceled()) {
            urlSessionTask!!.cancel()
        }
        urlSessionTask = null
        afterProcess = null
        resumeProcessList.clear()
        handler.removeCallbacks(invokeAfterProcess)
        invokeSuccessProcess?.let {
            handler.removeCallbacks(it)
        }
        state = TaskState.CANCEL
    }

    fun after(delaySec: Double, process: () -> Unit) {
        afterProcess = process
        afterProcessDelaySec = delaySec
    }

    fun startAfterProcessTimer() {
        val delaySec = afterProcessDelaySec
        if (afterProcess != null && delaySec != null) {
            handler.postDelayed(invokeAfterProcess, (delaySec * 1000).toLong())
        }
    }

    /**
     * run this task's after process
     */
    open val invokeAfterProcess = Runnable {
        //store task process
        if (null == autoRefreshTaskEnable) {
            afterProcess?.invoke()
            return@Runnable
        }
        afterProcess?.let {
            this.resumeProcessList.add {
                afterProcess?.invoke()
            }
        }
        val runnableCode = object : Runnable {
            override fun run() {
                //check if able to run all resume process
                if (autoRefreshTaskEnable!!.invoke()) {
                    //clear all resume process
                    while (resumeProcessList.size > 0) {
                        resumeProcessList.first().invoke()
                        resumeProcessList.removeFirst()
                    }
                } else {
                    //recheck
                    handler.postDelayed(this, (afterProcessDelaySec!! * 1000).toLong())
                }
            }
        }
        runnableCode.run()
    }

    //editable start
    open var timeoutIntervalForRequest: TimeInterval = DefaultSetting.timeoutIntervalForRequest
    open var timeoutIntervalForResource: TimeInterval = DefaultSetting.timeoutIntervalForResource
    open var retryTimes: Int = DefaultSetting.retryTimes
    open var auth = DefaultSetting.auth

    private var mockName: String? = null

    private var autoRefreshTaskEnable: (() -> Boolean)? = null

    private var postType: PostType = PostType.JSON

    fun post(postType: PostType): HttpTask<DataType> {
        httpMethod = "POST"
        this.postType = postType
        return this
    }

    open fun customizeBody(): RequestBody? {
        return null
    }

    fun autoRefresh(state: (() -> Boolean)): HttpTask<DataType> {
        return this.autoRefresh(state, DefaultSetting.autoRefreshInterval)
    }

    fun autoRefresh(state: (() -> Boolean), interval: Double): HttpTask<DataType> {
        if (null == poolName) {
            this.autoRefreshTaskEnable = state
            this.after(interval, this::autoDoing)
        } else {
            Log.i(
                "auto refresh error",
                "auto refresh avoid, failed since this is a multi send task"
            )
        }
        return this
    }

    /**
     * add mock data to replace the real response
     * @param mockName String data file name
     * @return HttpTask<DataType>
     */
    open fun mock(mockName: String): HttpTask<DataType> {
        this.mockName = mockName
        return this
    }

    private fun autoDoing() {
        this.execute(successHandler)
    }

    /**
     * the multi task pool name(@TaskManager) that current task belong
     */
    private var poolName: String? = null

    /**
     * multi task send mode
     * @param poolName String
     */
    fun poolControlByName(poolName: String): HttpTask<DataType> {
        if (null == autoRefreshTaskEnable) {
            this.poolName = poolName
            TaskManager.registTask(poolName, this)
        } else {
            Log.i(
                "task multi send error",
                "multi send avoid, failed since this is an auto refresh task "
            )
        }
        return this
    }

    override var state: TaskState = TaskState.START
        set(value) {
            field = value
            onTaskStateChange(value)
        }

    override fun onTaskStateChange(state: TaskState) {
        if (null == poolName || null == TaskManager.taskPool[poolName!!]) return
        TaskManager.taskPool[poolName!!]?.forEach {
            if (it.state != TaskState.CANCEL && it.state != TaskState.SUCCESS && it.state != TaskState.ERROR) {
                return
            }
        }
        val resultStates = TaskManager.taskPool[poolName!!]?.map { it.state }
        TaskManager.taskCallBacks[poolName!!]?.invoke(resultStates)
        TaskManager.clear(poolName!!)
    }
    //multi send end

    //editable end

    abstract val resultClass: KClass<DataType>

    open val requestURL: String
        get() {
            return baseURL + path
        }

    open val baseURL: String
        get() {
            return ""
        }
    open val path: String
        get() {
            return ""
        }
    open var httpMethod: String = "GET"

    open val headers: Map<String, String>
        get() {
            return mapOf()
        }
    open val requestEncoding: String = Charsets.UTF_8.displayName()

    /**
     * request params init et..
     */
    open fun prepareRequest() {}

    open fun isValidResponse(response: DataType): Boolean {
        return true
    }

    open fun preProcess(result: DataType) {}

    open fun postProcess(result: DataType) {
        if (null != afterProcess) {
            startAfterProcessTimer()
        }
    }

    open fun errorProcess(type: HttpTaskError, result: DataType?, info: ResponseInfo) {}

    @Throws(java.lang.Exception::class)
    open fun convert(data: ResponseBody): DataType {
        val text = data.string()
        if (debug) {
            Log.e("request ${this::class.java.simpleName}", text)
        }
        return parseResponse(text)
    }

    open fun parseResponse(data: String): DataType {
        throw HttpTaskError.Parse
    }

    fun checkStatusCode(code: Int): Boolean {
        return code == 200
    }

    fun updateIndicator() {
        val view = indicator
        if (view != null) {
            view.visibility = if (0 < view.intTag) View.VISIBLE else View.GONE
        }
    }

    fun addQuery(key: String, value: Any?) {
        value?.let {
            queryItems.append(Pair(key, value))
        }
    }

    open fun makeQueryString(): String? {
        return QueryUtils.makeQueryString(items = queryItems, encoder = parameterEncoder)
    }

    open val parameterEncoder: ((String) -> (String))? = null
}

sealed class HttpTaskError : Exception() {
    object Network : HttpTaskError()
    object StatusCode : HttpTaskError()
    object Parse : HttpTaskError()
    object InvalidResponse : HttpTaskError()

}

enum class ErrorHandlingStatus {
    FINISHED,
    CONTINUING
}

object CommonUserAgent {
    var value: String? = null
}

enum class PostType {
    JSON,
    FORM,
}
