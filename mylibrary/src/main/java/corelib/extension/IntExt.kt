package corelib.extension

/**
 *
 * Created by yamamoto on 2018/01/26.
 */
val Int.description: String
    get() = toString()

val Long.description: String
    get() = toString()

val Int?.longValue: Long?
    get() = this?.toLong()