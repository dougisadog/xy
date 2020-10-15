package com.shuange.lesson.service.response.bean

import com.shuange.lesson.Storable

open class BaseBean: Storable {
    open val id: Long = 0
    open var createdBy: String = ""
    open var createdDate: String = ""
    open var lastModifiedBy: String = ""
    open var lastModifiedDate: String = ""
}