package com.shuange.lesson.base

import com.shuange.lesson.base.bean.AccountBean
import com.shuange.lesson.service.response.bean.PairLessonType
import com.shuange.lesson.service.response.bean.ShortVideoType

object LessonDataCache {

    var token: String = ""

    var account: AccountBean? = null

    fun accountData(): AccountBean {
        return requireNotNull(account)
    }

    var types = mutableListOf<PairLessonType>()

    var shortVideoTypes = mutableListOf<ShortVideoType>()

}