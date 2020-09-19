package com.shuange.lesson.utils

import android.widget.Toast
import corelib.util.ContextManager

object ToastUtil {

    fun show(message: String, last: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(ContextManager.getContext(), message, last).show()
    }
}