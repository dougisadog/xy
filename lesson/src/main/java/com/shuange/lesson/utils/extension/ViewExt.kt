package com.shuange.lesson.utils.extension

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.view.NonDoubleClickListener
import corelib.util.ContextManager

fun View.onClick(click: EmptyTask) {
    setOnClickListener(NonDoubleClickListener {
        click?.invoke()
    })
}

fun Int.colorValue(): Int {
    return ContextCompat.getColor(ContextManager.getContext(), this)
}