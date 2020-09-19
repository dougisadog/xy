package corelib.extension

fun <T> Array<T>.index(of: T): Int? {
    val index = indexOf(of)
    return if (index != -1) index else null
}