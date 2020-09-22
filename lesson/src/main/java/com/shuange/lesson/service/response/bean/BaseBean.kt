package com.shuange.lesson.service.response.bean

open class BaseBean {
    open val id: Int = 0
    open var createdBy: String = ""
    open var createdDate: String = ""
    open var lastModifiedBy: String = ""
    open var lastModifiedDate: String = ""
}