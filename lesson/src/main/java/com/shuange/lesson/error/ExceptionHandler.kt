package com.shuange.lesson.error


import android.os.Build
import android.text.TextUtils
import corelib.util.ContextManager
import corelib.versionCheck.ApkInfo
import java.io.*
import java.util.*


class ExceptionHandler(private val previousHandler: Thread.UncaughtExceptionHandler) :
    Thread.UncaughtExceptionHandler {

    companion object {
        fun getLogDic(): String? {
            //storage/emulated/0/Android/data/com.meten.xyh/cache/log/
            val externalCacheDir = ContextManager.getContext().externalCacheDir ?: return null
            val dic = externalCacheDir.absolutePath + File.separator + "log" + File.separator
            try {
                if (!File(dic).exists()) {
                    File(dic).mkdirs()
                }
            } catch (e: Exception) {
            }
            return dic
        }
    }

    override fun uncaughtException(
        thread: Thread,
        exception: Throwable
    ) {
        val now = Date()
        val result: Writer = StringWriter()
        val printWriter = PrintWriter(result)
        exception.printStackTrace(printWriter)
        try {
            val logDir = getLogDic()
            if (TextUtils.isEmpty(logDir)) {
                return
            }
            val filename = UUID.randomUUID().toString()
            val path = logDir + File.separator + filename + ".log"
            val write = BufferedWriter(FileWriter(path))
            ApkInfo.toString()
            write.write(
                """
                    Package: ${ApkInfo.packageName.toString()}
                    
                    """.trimIndent()
            )
            write.write(
                """
                    Version: ${ApkInfo.versionName.toString()}
                    
                    """.trimIndent()
            )
            write.write(
                """
                    Android: ${Build.VERSION.SDK_INT.toString()}
                    
                    """.trimIndent()
            )
            write.write(
                """
                    Manufacturer: ${Build.MANUFACTURER.toString()}
                    
                    """.trimIndent()
            )
            write.write(
                """
                    Model: ${Build.MODEL.toString()}
                    
                    """.trimIndent()
            )
            write.write("Date: $now\n")
            write.write("\n")
            write.write(result.toString())
            write.flush()
            write.close()
        } catch (another: Exception) {
        } finally {
        }
        previousHandler.uncaughtException(thread, exception)
    }

}