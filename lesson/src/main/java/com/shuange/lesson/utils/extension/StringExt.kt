package com.shuange.lesson.utils.extension

fun String.force2Long(): Long {
    try {
        return toLong()
    } catch (e: Exception) {
    }
    return 0
}