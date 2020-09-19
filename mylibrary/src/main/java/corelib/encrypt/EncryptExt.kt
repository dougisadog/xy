package corelib.encrypt

import java.security.MessageDigest

fun String.sha1(): String {
    return this.shaString("SHA-1")
}

fun String.sha256(): String {
    return this.shaString("SHA-256")
}

fun String.shaString(type: String): String {
    val HEX_CHARS = "0123456789abcdef"
    val bytes = MessageDigest.getInstance(type).digest(this.toByteArray())
    val result = StringBuffer(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(HEX_CHARS[i shr 4 and 0x0f])
        result.append(HEX_CHARS[i and 0x0f])
    }
    return result.toString()
}