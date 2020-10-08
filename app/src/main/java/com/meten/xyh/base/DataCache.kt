package com.meten.xyh.base

import com.meten.xyh.modules.login.AccountBean
import com.meten.xyh.modules.user.bean.UserBean
import com.meten.xyh.service.response.bean.SubUser

object DataCache {

    var account: AccountBean? = null

    fun accountData(): AccountBean {
        return requireNotNull(account)
    }

    var users = mutableListOf<UserBean>()

    fun currentUser(): UserBean? {
        return users.firstOrNull { it.current }
    }

    //创建新对象
    var newSubUser: SubUser? = null
}