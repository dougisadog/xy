package corelib.extension

/**
 *
 * Created by Yamamoto Keita on 2018/01/18.
 */
fun <K, T> MutableMap<K, T>.removeAll() {
    clear()
}