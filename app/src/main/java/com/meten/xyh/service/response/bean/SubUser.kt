package com.meten.xyh.service.response.bean

import com.shuange.lesson.service.response.bean.BaseBean
import com.shuange.lesson.utils.RequestClass

@RequestClass
class SubUser {
    var id: Int = -1
    var age: Int = 0
    var appInfoId: Int = 0
    var avatarUrl: String = ""
    var city: String = ""
    var gender: String = ""
    var interest: String = ""
    var isCurrent: Boolean = false
    var name: String = ""
    var objective: String = ""
    var phone: String = ""
    var province: String = ""
    var stage: String = ""
    var userId: Int = -1
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