package corelib.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ContextManager {

    private var context: Application? = null

    fun init(c: Application) {
        context = c
    }

    fun getContext(): Application {
        return requireNotNull(context)
    }
}