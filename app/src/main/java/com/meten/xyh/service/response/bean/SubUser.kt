package com.meten.xyh.service.response.bean

import com.shuange.lesson.Storable
import com.shuange.lesson.enumeration.Gender
import com.shuange.lesson.utils.RequestClass

@RequestClass
class SubUser: Storable {
    var id: Long = -1
    var age: Int? = null
    var appInfoId: Long = 0
    var avatarUrl: String? = null
    var city: String? = null
    var gender: String = Gender.UNKNOWN.name
    var interest: String? = null
    var isCurrent: Boolean = false
    var name: String = ""
    var objective: String? = null
    var phone: String? = null
    var province: String? = null
    var stage: String? = null
    var userId: Long = -1
}
//description:
//用户
//
//age	integer($int32)
//年纪
//
//appInfoId*	integer($int64)
//appInfo
//
//avatarUrl	string
//头像
//
//city	string
//市
//
//createdBy	string
//创建人
//
//createdDate	string($date-time)
//创建时间
//
//gender	string
//性别
//
//Enum:
//Array [ 3 ]
//id	integer($int64)
//interest	string
//兴趣
//
//isCurrent*	boolean
//当前用户
//
//lastModifiedBy	string
//修改人
//
//lastModifiedDate	string($date-time)
//修改时间
//
//name*	string
//姓名
//
//objective	string
//目的
//
//phone	string
//提醒手机
//
//province	string
//省
//
//stage	string
//阶段
//
//userId*	integer($int64)
//账号