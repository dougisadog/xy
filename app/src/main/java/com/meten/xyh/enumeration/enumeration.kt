package com.meten.xyh.enumeration

enum class UserSettingType {
    STAGE, INTEREST, OBJECTIVE
}

enum class SearchType {
    TEACHER, COURSE, NEWS
}

enum class SignatureType {
    SIGNATURE, NICKNAME
}

enum class PayType(val text: String) {
    WX("微信支付"), ALIPAY("支付宝")
}

enum class PayStateType(val text: String) {
    SUCCESS("支付成功"), FAILED("支付失败")
}

enum class OrderState(val text: String) {
    ALL("全部"), PENDING("待支付"), FINISHED("已完成")
}


