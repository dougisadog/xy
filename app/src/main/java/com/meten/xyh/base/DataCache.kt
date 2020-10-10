package com.meten.xyh.base

import com.meten.xyh.modules.user.bean.UserBean
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.LessonDataCache

object DataCache {

    var users = mutableListOf<UserBean>()

    fun currentUser(): UserBean? {
        return users.firstOrNull { it.current }
    }

    //创建新对象
    var newSubUser: SubUser? = null

    fun generateNewSubUser(isCreate: Boolean): SubUser? {
        if (isCreate) {
            if (null == newSubUser) {
                newSubUser =
                    SubUser().apply { userId = LessonDataCache.account?.id?.toLong() ?: -1 }
            }
            return newSubUser
        } else {
            return currentUser()?.subUser
        }
    }
}