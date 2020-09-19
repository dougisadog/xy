package corelib.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

open class RetryInterceptor(retry: Int? = 3) : Interceptor {

    var retryTimes:Int = retry!!

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var tryCount: Int = 0
        var response: Response? = null
        while (tryCount < retryTimes) {
            try {
                response = chain.proceed(request)
            } catch (e: Exception) {
                tryCount++
            }
            if (null == response || !response.isSuccessful) {
                tryCount++
            } else {
                return response
            }
        }
        return chain.proceed(request)
    }
}