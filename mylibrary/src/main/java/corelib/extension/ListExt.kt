package corelib.extension

/**
 *
 * Created by Yamamoto Keita on 2018/01/18.
 */

fun <T> MutableList<T>.append(value: T) {
    add(value)
}

fun <T> MutableList<T>.append(contentsOf: List<T>) {
    addAll(contentsOf)
}

fun <T> MutableList<T>.removeLast(): T? {
    if (size == 0) return null
    val element: T = get(size - 1)
    removeAt(size - 1)
    return element
}

fun <T> MutableList<T>.removeFirst(): T {
    return removeAt(0)
}

fun <T> MutableList<T>.removeSubrange(range: IntRange) {
    if (range.first < range.last) {
        val remove = subList(range.first, range.last).toList()
        removeAll(remove)
    }
}

fun <T> MutableList<T>.removeAll() {
    clear()
}

fun <T> MutableList<T>.remove(elements: List<T>) {
    elements.forEach {
        remove(it)
    }
}

fun List<String>.joined(separator: String = ""): String {
    return joinToString(separator = separator)
}

fun <T> List<T>.index(of: T): Int {
    return indexOf(of)
}

fun <T> List<T>.contains(where: (T) -> Boolean): Boolean {
    return firstOrNull(where) != null
}

fun <T, R: Any> List<T>.flatMap(transform: (T) -> R?): List<R> {
    return map(transform).filterNotNull()
}

val <T> List<T>.count: Int get() = size

fun <T> List<T>.safeGet(index: Int): T? {
    if (index in 0..(size - 1)) {
        return this[index]
    }
    return null
}

fun <T> List<T>.indexOrNull(predicate: (T) -> Boolean): Int? {
    val index = indexOfFirst(predicate)
    if (index == -1) {
        return null
    }
    return index
}

fun <T> MutableList<T>.insert(value: T, index: Int) {
    add(index, value)
}

fun <T> List<T>.enumerated(): List<ListEnumerationItem<T>> {
    return mapIndexed {index, element ->
        ListEnumerationItem(index, element)
    }
}

data class ListEnumerationItem<T>(val offset: Int, val element: T)