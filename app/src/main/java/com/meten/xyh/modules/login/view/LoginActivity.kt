package com.meten.xyh.modules.login.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityLoginBinding
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.modules.login.viewmodel.LoginViewModel
import com.meten.xyh.modules.usersetting.view.BaseUserSettingActivity
import com.meten.xyh.utils.BusinessUtil
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener


/**
 * 登录
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, LoginActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: LoginViewModel by viewModels {
        BaseShareModelFactory()
    }


    override val layoutId: Int
        get() = R.layout.activity_login
    override val viewModelId: Int
        get() = BR.loginViewModel


    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            viewModel.testLogin {
//            viewModel.login {
                BaseUserSettingActivity.start(this, UserSettingType.STAGE, false)
            }
        })
        binding.verifyCodeTv.setOnClickListener(NonDoubleClickListener {
            sendVerifyMessage()
        })
    }

    private fun sendVerifyMessage() {
        if (viewModel.checkPhone()) {
            binding.verifyCodeTv.isEnabled = false
            viewModel.sendVerifyCode()
        }
    }


    override fun initViewObserver() {
        viewModel.username.observe(this, Observer {
            checkLogin()
        })
        viewModel.verifyCode.observe(this, Observer {
            checkLogin()
        })
        viewModel.confirmCheck.observe(this, Observer {
            checkLogin()
        })
        BusinessUtil.addVerifyListener(viewModel.remainTime, this, binding.verifyCodeTv)
    }

    private fun checkLogin() {
        val isEnable =
            !viewModel.username.value.isNullOrBlank() && !viewModel.verifyCode.value.isNullOrBlank() && viewModel.confirmCheck.value == true
        binding.nextTv.alpha = if (isEnable) 1f else 0.55f
        binding.nextTv.isEnabled = isEnable
    }



}