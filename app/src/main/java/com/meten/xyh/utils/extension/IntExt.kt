package com.meten.xyh.utils.extension

import corelib.util.ContextManager
import corelib.util.DeviceUtils

fun Int.toPx(): Int {
    return DeviceUtils.toPx(ContextManager.getContext(), this)
}