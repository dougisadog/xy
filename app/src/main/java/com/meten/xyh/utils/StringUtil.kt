package com.meten.xyh.utils

import java.util.regex.Pattern

object StringUtil {
    fun checkPhone(phoneNum: String?): Boolean {
        if (null == phoneNum) return false
        val regex = "^(1[3-9]\\d{9}$)"
        if (phoneNum.length == 11) {
            val p = Pattern.compile(regex);
            val m = p.matcher(phoneNum);
            if (m.matches()) {
                return true
            }
        }
        return false
    }
}