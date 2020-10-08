package com.shuange.lesson.base

import com.shuange.lesson.base.bean.AccountBean

object LessonDataCache {

    var token: String = ""

    var account: AccountBean? = null

    fun accountData(): AccountBean {
        return requireNotNull(account)
    }
}