package corelib.extension

import android.content.res.TypedArray

fun TypedArray.getFloatOrNull(id: Int, defValue: Float): Float? {
    return if (hasValue(id)) getFloat(id, defValue) else null
}

fun TypedArray.getIntOrNull(id: Int, defValue: Int = 0): Int? {
    return if (hasValue(id)) getInt(id, defValue) else null
}

fun TypedArray.getStringOrNull(id: Int): String? {
    return if (hasValue(id)) getString(id) else null
}

fun TypedArray.getBooleanOrNull(id: Int, defValue: Boolean): Boolean? {
    return if (hasValue(id)) getBoolean(id, defValue) else defValue
}

fun TypedArray.getResourceIdOrNull(id: Int): Int? {
    return if (hasValue(id)) getResourceId(id, -1) else null
}