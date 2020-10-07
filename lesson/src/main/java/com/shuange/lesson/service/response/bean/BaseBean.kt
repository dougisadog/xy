package com.shuange.lesson.service.response.bean

open class BaseBean {
    open val id: Long = 0
    open var createdBy: String = ""
    open var createdDate: String = ""
    open var lastModifiedBy: String = ""
    open var lastModifiedDate: String = ""
}