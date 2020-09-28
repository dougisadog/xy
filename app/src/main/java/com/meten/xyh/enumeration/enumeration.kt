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

