package com.meten.xyh.modules.user.bean

import com.meten.xyh.base.DataCache
import com.shuange.lesson.modules.lesson.bean.SourceData
import java.io.File

class UserBean private constructor(val accountId: String, var userName: String) {

    companion object {
        fun createUserInfo(name: String, header: String): UserBean? {
            val accountId = DataCache.account?.id
            if (name.isBlank() || accountId.isNullOrBlank()) return null
            return UserBean(accountId, name).apply {
                headerImage = buildSourceDataByLink(header)
            }
        }
    }

    var current = false
    var headerImage: SourceData? = null

    private fun buildSourceDataByLink(
        link: String
    ): SourceData {
        val dic: String = "user" + File.separator + this.accountId
        return SourceData().apply {
            name = userName
            dictionary = dic
            url = link
            val arr = link.split(".")
            suffix = if (arr.size > 1) arr[arr.size - 1] else ""
        }
    }
}