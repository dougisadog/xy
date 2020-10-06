package com.meten.xyh.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object TimeUtil {

    val UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    val defaultOutPut = "yyyy-MM-dd   HH:mm:ss"

    @SuppressLint("SimpleDateFormat")
    fun parseTime(
        input: String,
        inputPattern: String = UTC_PATTERN,
        outPutPattern: String = defaultOutPut
    ): String {
        try {
            val df = SimpleDateFormat(inputPattern)
            val date = df.parse(input) ?: return ""
            return SimpleDateFormat(defaultOutPut).format(date)
        } catch (e: Exception) {
        }
        return ""
    }
}