package com.meten.xyh.utils.extension

import android.view.View

fun View.setCustomEnable(enable: Boolean) {
    alpha = if (enable) 1f else 0.55f
    isEnabled = enable
}