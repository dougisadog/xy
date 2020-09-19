package corelib.extension

import java.net.URLDecoder
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 *
 * Created by Yamamoto Keita on 2018/01/17.
 */
fun String.data(using: Charset): ByteArray {
    return toByteArray(using)
}

fun String.components(separatedBy: String): List<String> {
    return split(separatedBy)
}

fun String.hasPrefix(prefix: String): Boolean {
    return startsWith(prefix)
}

fun String.hasSuffix(suffix: String): Boolean {
    return endsWith(suffix)
}

fun String.substringTo(to: Int): String {
    return substring(0, to)
}

fun String.removeLast(suffix: String): String {
    return if (endsWith(suffix)) substringTo(length - suffix.length) else this
}

fun String.replacingOccurrences(of: String, with: String): String {
    return replace(of, with)
}

fun String.prefix(length: Int): String {
    return substringTo(length)
}

fun String.lowercased(): String {
    return toLowerCase()
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

fun String.remove(of: String): String {
    return replacingOccurrences(of, "")
}

fun String.remove(of: List<String>): String {
    var value = this
    of.forEach { value = value.remove(of = it) }
    return value
}

fun String.trim(target: String): String {
    return replacingOccurrences(target, "")
}

val String.doubleValue: Double
    get() = toDoubleOrNull() ?: 0.0

fun String.append(value: String): String {
    return this + value
}

val String.removingPercentEncoding: String
    get() = URLDecoder.decode(this, "UTF-8")

fun String.dropLast(): String {
    return substring(0, length - 1)
}

val String?.count: Int
    get() = this?.length ?: 0

fun String.range(text: String): IntRange? {
    val start = indexOf(text)
    if (start == -1) return null
    return IntRange(start, start + text.length)
}

val String.hashValue: Int
    get() = hashCode()

val String.size: Int
    get() = length

public operator fun String.get(range: IntRange): String {
    return substring(range)
}


/**
 *
 * Created by yamamoto on 2018/01/25.
 */
fun String.toSnakeCase(): String {
    var canGoNextBlock = false
    return map {
        val separator = if (canGoNextBlock && it.isUpperCase()) "_" else ""
        canGoNextBlock = it.isLowerCase()
        separator + it.toLowerCase()
    }.joinToString("")
}

val String?.isEmpty: Boolean
    get() {
        return this?.isEmpty() ?: true
    }

val String?.isNotEmpty: Boolean
    get() {
        return this?.isNotEmpty() ?: false
    }

val String?.isNumber: Boolean
    get() {
        if (this != null) {
            try {
                toDouble()
                return true
            } catch (e: Exception) {
            }
        }
        return false
    }

val String?.integerValue: Int
    get() {
        try {
            return this?.toInt() ?: 0
        } catch (e: NumberFormatException) {
        }
        return 0
    }
val String?.longValue: Long
    get() {
        try {
            return this?.toLong() ?: 0
        } catch (e: NumberFormatException) {
        }
        return 0
    }

fun String.split(length: Int): List<String> {
    val array = mutableListOf<String>()
    var i = 0
    while (i < this.length) {
        array.append(substring(i, i + length))
        i += length
    }
    return array
}

fun String.hasAnyPrefix(prefixes: List<String>): Boolean {
    return prefixes.contains { this.hasPrefix(it) }
}

fun String.anyPrefix(prefixes: List<String>): String? {
    return if (hasAnyPrefix(prefixes)) substring(0, 1) else null
}

fun String?.removePrefix(prefix: String): String {
    if (this?.hasPrefix(prefix).check) {
        return this?.drop(prefix.count) ?: this ?: ""
    }
    return this ?: ""
}

/** 特定の文字に囲まれた文字列を取得する */
fun String.enclosed(left: String, right: String): String? {

    val startIndex = this.indexOf(left)
    if (startIndex != -1) {
        var endIndex = this.length

        val index = this.lastIndexOf(right)
        if (index != -1) {
            endIndex = index
        }
        return this.substring(startIndex + 1, endIndex)
    }
    return null
}


/** ShiftJISに変換できない文字列を削除する */
fun String.removeNonShiftJis(): String? {
    val charset = Charset.forName("MS932")
    val removedStringList = map { it.toString() }.filter {
        val sb = StringBuilder()
        it.toByteArray().map {
            String.format("%02X", it)
        }.forEach {
            sb.append(it)
        }
        return@filter it.data(using = charset).toString(charset) == it
    }
    return if (removedStringList.count > 0) removedStringList.reduce { value1, value2 -> value1 + value2 } else null
}

/** 小数点以下が0の場合は小数点以下を除去する */
fun String.removeDotZero(): String? {
    if (isEmpty || !contains(".")) {
        return this
    }
    val values = split(".")
    return if (values[1] == "0") values[0] else this
}
