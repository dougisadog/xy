package corelib.http.cookie

import okhttp3.Cookie

val Cookie.hasExpired: Boolean
    get() {
        return expiresAt < System.currentTimeMillis()
    }

fun Cookie.keyEquals(other: Any?): Boolean {
    if (other !is Cookie) return false
    val that = other as Cookie?
    return that?.name == name && that.domain == domain && that.path == path
}