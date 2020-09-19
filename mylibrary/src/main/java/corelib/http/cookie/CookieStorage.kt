package corelib.http.cookie

import okhttp3.Cookie
import okhttp3.HttpUrl

object CookieStorage {

    val shared: CookieStorage
        get() = this

    private var cookieMap: MutableMap<String, MutableList<Cookie>> = mutableMapOf()

    fun setCookies(url: HttpUrl, cookies: MutableList<Cookie>) {
        val targetList = cookieMap[url.host] ?: mutableListOf()
        cookies.forEach { cookie ->
            targetList.removeAll { it.keyEquals(cookie) }
        }
        targetList.addAll(cookies)
        cookieMap[url.host] = targetList
    }

    fun getCookies(url: HttpUrl): List<Cookie> {
        val host = url.host
        removeExpiredCookies(host)

        val path = url.encodedPath
        val targetList = cookieMap[host] ?: return mutableListOf()

        return targetList.filter {
            path.startsWith(it.path) && it.domain == host
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun removeExpiredCookies(host: String) {
        val targetList = cookieMap[host] ?: return
        targetList.removeAll { it.hasExpired }
        cookieMap[host] = targetList
    }

    fun clear() {
        cookieMap.clear()
    }

}