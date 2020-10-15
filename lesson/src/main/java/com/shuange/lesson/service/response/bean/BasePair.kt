package com.shuange.lesson.service.response.bean

import com.shuange.lesson.Storable

/**
 * 基本pair类型
 */
open class BasePair:Storable {
    var value: String? = null
    var name: String? = null
}