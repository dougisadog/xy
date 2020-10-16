package com.shuange.lesson.error


import android.text.TextUtils
import java.io.File
import java.io.FilenameFilter
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object ErrLogManager {
    private const val MAX_LOG_FILES = 10
    fun registerHandler() {
        val currentHandler =
            Thread.getDefaultUncaughtExceptionHandler()

        // Register if not already registered
        if (currentHandler !is ExceptionHandler) {
            Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(currentHandler))
        }
        val scheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor()
        scheduledExecutorService.schedule(ClearLogTask(), 5, TimeUnit.SECONDS)
    }

    private fun onlyLeftTenLogFilesInStorage() {
        val files = searchForStackTraces() ?: return
        val length = files.size
        if (length > MAX_LOG_FILES) {
            for (i in MAX_LOG_FILES until length) {
                File(files[i]).delete()
            }
        }
    }

    private fun searchForStackTraces(): Array<String?> {
        val path = ExceptionHandler.getLogDic()
        if (TextUtils.isEmpty(path)) {
            return arrayOfNulls(0)
        }
        // Try to create the files folder if it doesn't exist
        val dir = File(path)

        // Filter for ".stacktrace" files
        val filter = FilenameFilter { dir, name -> name.endsWith(".log") }
        val files = dir.list(filter)
        for (i in files.indices) {
            files[i] = path + File.separator + files[i]
        }
        //desc sort arrays, then delete the longest file
        Arrays.sort(files) { aFilePath, bFilePath ->
            val aDate = File(aFilePath).lastModified()
            val bDate = File(bFilePath).lastModified()
            if (aDate > bDate) -1 else 1
        }
        return files
    }

    private class ClearLogTask : Runnable {
        override fun run() {
            onlyLeftTenLogFilesInStorage()
        }
    }
}
