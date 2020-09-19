package com.shuange.lesson.utils

import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

object Base64Util {

    fun encodeFileToBase64Binary(fileName: String): String? {

        val file = File(fileName)
        val bytes = loadFile(file)
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun loadFile(file: File): ByteArray {
        val isInputStream: InputStream = FileInputStream(file)
        val length: Long = file.length()
        val bytes = ByteArray(length.toInt())
        var offset = 0
        var numRead = 0
        try {
            while (offset < bytes.size
                && isInputStream.read(bytes, offset, bytes.size - offset)
                    .also { numRead = it } >= 0
            ) {
                offset += numRead
            }
            if (offset < bytes.size) {
                throw IOException("Could not completely read file " + file.name)
            }
        } catch (e: Exception) {

        } finally {
            try {
                isInputStream.close()
            } catch (e: Exception) {
            }
        }
        return bytes
    }
}