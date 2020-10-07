package com.meten.xyh.service.response.bean

import com.shuange.lesson.service.response.bean.BaseBean

class Account : BaseBean() {
    val activated: Boolean = false
    val appId: String = ""
    val appInfoId: Long = 0
    val appInfoName: String = ""
    val authorities: List<String> = arrayListOf()
    val avatarUrl: String = ""
    val city: String = ""
    val fromUserId: String = ""
    val gender: String = ""
    val login: String = ""
    val name: String = ""
    val phone: String = ""
    val province: String = ""
    val roleType: String = ""
    val money: Int = 0
}