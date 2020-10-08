package com.shuange.lesson.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import corelib.util.Log

object ActivityUtil {

    fun startOutsideActivity(
        context: Context,
        className: String,
        intentBuilder: ((Intent) -> Unit)? = null
    ) {
        try {
            val classFullPath = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_ACTIVITIES
            ).activities.firstOrNull {
                it.name.endsWith(
                    className
                )
            }?.name
            classFullPath?.let {
                val i = Intent(context, Class.forName(classFullPath))
                intentBuilder?.invoke(i)
                context.startActivity(i)
            }
        } catch (e: Exception) {
            Log.e("startOutsideActivity", "class: $className error: ${e.message}")
        }
    }
}