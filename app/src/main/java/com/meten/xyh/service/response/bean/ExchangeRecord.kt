package com.meten.xyh.service.response.bean

import com.shuange.lesson.service.response.bean.BaseBean

class ExchangeRecord : BaseBean() {
    var exchangeCodeId: Long = 0
    var exchangeCodeNo: String? = null
    var lessonPackageId: Long = 0
    var lessonPackageName: String? = null
    var login: String? = null
    var phone: String? = null
    var userId: Long = 0
}