package corelib.http

import android.annotation.SuppressLint
import corelib.TimeInterval
import java.security.cert.X509Certificate
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object DefaultSetting {

    // 自動更新ON / OFF
    var autoRefresh: Boolean = true
    // 自動更新間隔
    var autoRefreshInterval: Double = 5.0

    //http default setting
    var timeoutIntervalForRequest: TimeInterval = 20.0
    var timeoutIntervalForResource: TimeInterval = 30.0
    var retryTimes: Int = 3
    //user auth header
    var auth: Pair<String, String>? = null

    var trustManager: X509TrustManager = defaultTrustManager()

    private fun defaultTrustManager(): X509TrustManager {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })
            return trustAllCerts[0] as X509TrustManager
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}