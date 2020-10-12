package com.meten.xyh.modules.login.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.modules.login.viewmodel.SetNewPasswordViewModel
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 设置新密码
 */
class SetNewPasswordActivity : SingleInputActivity<SetNewPasswordViewModel>() {

    companion object {
        fun start(context: Context, phone: String) {
            val i = Intent(context, SetNewPasswordActivity::class.java)
            i.putExtra(IntentKey.PHONE_KEY, phone)
            context.startActivity(i)
        }
    }

    override val viewModel: SetNewPasswordViewModel by viewModels {
        BaseShareModelFactory()
    }

    override fun initParams() {
        super.initParams()
        viewModel.username.value = intent.getStringExtra(IntentKey.PHONE_KEY)
    }
}