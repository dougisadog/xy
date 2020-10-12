package com.meten.xyh.modules.login.view

import android.content.Context
import android.content.Intent
import android.text.InputType
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.meten.xyh.modules.login.viewmodel.ForgetPasswordViewModel
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 忘记密码
 */
class ForgetPasswordActivity : SingleInputActivity<ForgetPasswordViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, ForgetPasswordActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: ForgetPasswordViewModel by viewModels {
        BaseShareModelFactory()
    }

    override fun initView() {
        super.initView()
        binding.inputEt.inputType = InputType.TYPE_CLASS_PHONE
    }

    override fun initViewObserver() {
        super.initViewObserver()
        viewModel.confirmed.observe(this, Observer {
            if (it == true) {
                SendVerifyActivity.start(this, viewModel.input.value ?: "")
            }
        })
    }
}