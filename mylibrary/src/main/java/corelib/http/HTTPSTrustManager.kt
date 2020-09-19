package corelib.http

/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * HTTPS Trust cert
 *
 * @author kymjs (http://www.kymjs.com/) on 9/23/15.
 */
class HTTPSTrustManager : X509TrustManager {

    @Throws(java.security.cert.CertificateException::class)
    override fun checkClientTrusted(
        x509Certificates: Array<java.security.cert.X509Certificate>, s: String
    ) {
        // To change body of implemented methods use File | Settings | File
        // Templates.
    }

    @Throws(java.security.cert.CertificateException::class)
    override fun checkServerTrusted(
        x509Certificates: Array<java.security.cert.X509Certificate>, s: String
    ) {
        // To change body of implemented methods use File | Settings | File
        // Templates.
    }

    fun isClientTrusted(chain: Array<X509Certificate>): Boolean {
        return true
    }

    fun isServerTrusted(chain: Array<X509Certificate>): Boolean {
        return true
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return _AcceptedIssuers
    }

    companion object {

        private var trustManagers: Array<TrustManager>? = null
        private val _AcceptedIssuers = arrayOf<X509Certificate>()

        fun allowAllSSL() {
            HttpsURLConnection.setDefaultHostnameVerifier { arg0, arg1 -> true }

            var context: SSLContext? = null
            if (trustManagers == null) {
                trustManagers = arrayOf(HTTPSTrustManager())
            }

            try {
                context = SSLContext.getInstance("TLS")
                context!!.init(null, trustManagers, SecureRandom())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }

            HttpsURLConnection.setDefaultSSLSocketFactory(
                context!!
                    .socketFactory
            )
        }
    }
}
