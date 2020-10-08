package com.meten.xyh.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.meten.xyh.R
import corelib.util.ContextManager

object BusinessUtil {

    fun checkValidPhone(phone: String): Pair<Boolean, String> {
        val phoneRegex = Regex("(13|14|15|17|18|19)[0-9]{9}")
        if (phoneRegex.matches(phone)) {
            return Pair(true, "")
        } else {
            return Pair(false, "手机号不合法")
        }
    }

    fun checkValidPwd(pwd: String): Pair<Boolean, String> {
        if (pwd.length > 16 || pwd.length < 8) {
            return Pair(false, "密码必须是8-16位")
        }
        val numberRegex = Regex("^[0-9]+\$")
        if (numberRegex.matches(pwd)) {
            return Pair(false, "不能是纯数字")
        }
        val pwdRegex = Regex("^[a-z0-9]+\$")
        if (pwdRegex.matches(pwd)) {
            return Pair(true, "")
        }
        return Pair(false, "密码必须是8-16位的数字，字符组合（不能是纯数字）")
    }

    /**
     * 添加验证码60s监听
     */
    fun addVerifyListener(
        remainTime: MutableLiveData<Int>,
        lifecycleOwner: LifecycleOwner,
        verifyCodeTv: TextView
    ) {
        remainTime.observe(lifecycleOwner, Observer {
            if (null == it) return@Observer
            if (it >= 0) {
                verifyCodeTv.text = "重新发送(${it}S)"
            } else {
                verifyCodeTv.text = ContextManager.getContext().getString(R.string.get_verify_code)
                verifyCodeTv.isEnabled = true
            }
        })
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    fun callPhone(
        context: Context,
        phoneNum: String = ContextManager.getContext().getString(R.string.support_tel)
    ) {
        val intent = Intent(Intent.ACTION_CALL)
        val data: Uri = Uri.parse("tel:$phoneNum")
        intent.data = data
        context.startActivity(intent)
    }

}

fun main(args: Array<String>) {
    val re = BusinessUtil.checkValidPwd("123a")

}