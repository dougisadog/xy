package com.shuange.lesson.base.bean

class AccountBean {

    var id: String = ""

    var phone: String = ""

    var xyBalance: Int = 0

    val textBalance:String
    get() {
        return xyBalance.toString()
    }

}