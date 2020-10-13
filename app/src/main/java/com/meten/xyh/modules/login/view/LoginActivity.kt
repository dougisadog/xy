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
import com.meten.xyh.utils.extension.setCustomEnable
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil
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
        checkLoginButton()
    }

    private fun initListener() {
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            viewModel.login {
                BaseUserSettingActivity.start(this, UserSettingType.STAGE, false)
                finish()
            }
        })
        binding.verifyInput.verifyCodeTv.setOnClickListener(NonDoubleClickListener {
            sendVerifyMessage()
        })
        binding.changeTypeTv.setOnClickListener(NonDoubleClickListener {
            viewModel.isVerifyMode.value = !(viewModel.isVerifyMode.value ?: false)
        })
        binding.forgetPwdTv.setOnClickListener(NonDoubleClickListener {
            //忘记密码
            ForgetPasswordActivity.start(this)
        })

        binding.contractTv.setOnClickListener(NonDoubleClickListener {
            ToastUtil.show("用户协议")
        })
        binding.privacyTv.setOnClickListener(NonDoubleClickListener {
            ToastUtil.show("隐私政策")
        })
    }

    private fun sendVerifyMessage() {
        if (viewModel.checkPhone()) {
            viewModel.sendVerifyCode {
                binding.verifyInput.verifyCodeTv.isEnabled = false
            }
        }
    }


    override fun initViewObserver() {
        viewModel.isVerifyMode.observe(this, Observer {
            if (it == true) {
                viewModel.title.value = "手机快捷登录/注册"
                viewModel.changeTypeName.value = "使用密码登录"
            } else {
                viewModel.title.value = "手机账号登录"
                viewModel.changeTypeName.value = "使用手机快捷登录"

            }
        })
        viewModel.username.observe(this, Observer {
            checkLoginButton()
        })
        viewModel.verifyCode.observe(this, Observer {
            checkLoginButton()
        })
        viewModel.confirmCheck.observe(this, Observer {
            checkLoginButton()
        })
        BusinessUtil.addVerifyListener(viewModel.remainTime, this, binding.verifyInput.verifyCodeTv)
    }

    private fun checkLoginButton() {
        val isVerifyMode = viewModel.isVerifyMode.value ?: return
        var isEnable =
            !viewModel.username.value.isNullOrBlank() && viewModel.confirmCheck.value == true
        isEnable =
            isEnable && !(if (isVerifyMode) viewModel.verifyCode else viewModel.password).value.isNullOrBlank()
        binding.nextTv.setCustomEnable(isEnable)
    }

}