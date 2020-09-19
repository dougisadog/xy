package corelib.http.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

 class CookieJarImpl: CookieJar {

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return CookieStorage.shared.getCookies(url).toMutableList()
    }

     override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
         CookieStorage.shared.setCookies(url, cookies.toMutableList())
     }
 }