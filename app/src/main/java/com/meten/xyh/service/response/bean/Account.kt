package com.meten.xyh.service.response.bean

import com.shuange.lesson.service.response.bean.BaseBean

class Account : BaseBean() {
    val activated: Boolean = false
    val appId: String? = null
    val appInfoId: Long = 0
    val appInfoName: String? = null
    val authorities: List<String> = arrayListOf()
    val avatarUrl: String? = null
    val city: String? = null
    val fromUserId: String? = null
    val gender: String? = null
    val login: String? = null
    val name: String? = null
    val phone: String? = null
    val province: String? = null
    val roleType: String? = null
    val money: Int = 0
}