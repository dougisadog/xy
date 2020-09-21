package com.shuange.lesson.modules.lesson.bean

import corelib.util.ContextManager
import java.io.File
import java.io.Serializable

class SourceData : Serializable {

    var name = ""
    var url = ""
    set(value) {
        field = value
        try {
            val arr = value.split(".")
            suffix = if (arr.size > 1) arr[arr.size - 1] else ""
        } catch (e: Exception) {
        }
    }

    var dictionary = ""

    //JPG MP4...
    var suffix = ""

    fun getDic(): String? {
        if (dictionary.isEmpty() || name.isEmpty()) return null
        val externalCacheDir = ContextManager.getContext().externalCacheDir ?: return null
        return externalCacheDir.absolutePath + File.separator + dictionary + File.separator
    }

    fun getFullPath(): String? {
        val dic = getDic() ?: return null
        return "${dic}$name.$suffix"
    }

    fun getSource(): File? {
        val path = getFullPath()?:return null
        try {
            File(path).let {
                if (it.exists() && it.isFile) {
                    return it
                }
            }
        } catch (e: Exception) {
        }
        return null
    }


}