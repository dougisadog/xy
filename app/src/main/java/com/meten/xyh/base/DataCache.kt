package com.meten.xyh.base

import com.meten.xyh.modules.login.AccountBean
import com.meten.xyh.modules.user.bean.UserBean

object DataCache {

    var account: AccountBean? = null

    fun accountData(): AccountBean {
        return requireNotNull(account)
    }

    var users = mutableListOf<UserBean>()

    fun currentUser(): UserBean? {
        return users.firstOrNull { it.current }
    }
}