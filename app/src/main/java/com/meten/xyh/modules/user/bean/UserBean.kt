package com.meten.xyh.modules.user.bean

import com.meten.xyh.service.response.bean.SubUser
import com.meten.xyh.service.response.bean.UserForAccount
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.modules.lesson.bean.SourceData
import java.io.File

class UserBean private constructor(val accountId: String) {

    companion object {
        fun createUserInfo(name: String, header: String? = null): UserBean {
            val accountId = LessonDataCache.account?.id
            return UserBean(accountId!!).apply {
                userName = name
                headerImage = header?.let { buildSourceDataByLink(it) }
            }
        }

        fun createBySubUser(subUser: SubUser): UserBean? {
            val userBean = createUserInfo(subUser.name)
            userBean.setSubUserData(subUser)
            return userBean
        }
    }

    fun setSubUserData(subUser: SubUser) {
            userName = subUser.name
            headerImage = subUser.avatarUrl?.let { it -> buildSourceDataByLink(it) }
            introduction = subUser.interest?:""
            current = subUser.isCurrent
            userId = subUser.userId.toString()
            this.subUser = subUser
    }

    var subUser: SubUser? = null
    var userRecord: UserForAccount? = null
    var userName: String = ""

    var userId: String = ""
    var current = false
    var headerImage: SourceData? = null
    var introduction: String = ""

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