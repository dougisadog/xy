package corelib.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.string(format: String): String {
    val result = SimpleDateFormat(format, Locale.US).format(this)
    return result ?: ""
}

fun Date.yearAdded(value: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.YEAR, value)
    return calendar.time
}

fun Date.monthAdded(value: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MONTH, value)
    return calendar.time
}

fun Date.dayAdded(value: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH, value)
    return calendar.time
}