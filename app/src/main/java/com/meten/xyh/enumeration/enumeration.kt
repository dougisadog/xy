package com.meten.xyh.enumeration

enum class UserSettingType {
    STAGE, INTEREST, OBJECTIVE, SIGNATURE, NICKNAME
}

enum class SearchType {
    TEACHER, COURSE, NEWS
}

enum class PayType(val text: String) {
    WECHAT("微信支付"), ALIPAY("支付宝"), APPLE("APPLE PAY");

    companion object {
        fun buildByText(text: String): OrderState {
            return OrderState.values().firstOrNull { it.name == text } ?: OrderState.ALL
        }
    }
}

enum class PayStateType(val text: String) {
    SUCCESS("支付成功"), FAILED("支付失败")
}

enum class OrderState(val text: String) {
    ALL("全部"), NEED_PAID("待支付"), PAID("已完成"), CANCEL("已取消");

    companion object {
        fun buildByText(text: String): OrderState? {
            return values().firstOrNull { it.name == text }
        }
    }
}

