package com.meten.xyh.service.response.bean

class Account (
    val activated: Boolean,
    val appId: String,
    val appInfoId: Int,
    val appInfoName: String,
    val authorities: List<String>,
    val avatarUrl: String,
    val city: String,
    val createdBy: String,
    val createdDate: String,
    val fromUserId: String,
    val gender: String,
    val id: Int,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val login: String,
    val name: String,
    val phone: String,
    val province: String,
    val roleType: String
)