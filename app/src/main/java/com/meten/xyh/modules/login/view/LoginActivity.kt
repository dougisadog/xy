package com.meten.xyh.modules.login.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.meten.xyh.databinding.ActivityLoginBinding
import com.meten.xyh.modules.login.viewmodel.LoginViewModel
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.modules.usersetting.view.BaseUserSettingActivity
import com.meten.xyh.modules.usersetting.view.StepActivity
import com.meten.xyh.utils.StringUtil
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.NonDoubleClickListener
import java.util.*


/**
 * 登录
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object {
        fun startLogin(context: Context) {
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
        //TODO
        viewModel.username.value = "18341134983"
    }

    private fun initListener() {
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            viewModel.login {
                BaseUserSettingActivity.start(this, UserSettingType.STAGE, false)
            }
        })
        binding.verifyCodeTv.setOnClickListener(NonDoubleClickListener {
            if ((StringUtil.checkPhone(viewModel.username.value))) {
                sendVerifyMessage()
            } else {
                ToastUtil.show("no valid phone number")
            }
        })
    }

    private fun sendVerifyMessage() {
        var remain = viewModel.maxTime
        binding.verifyCodeTv.isEnabled = false
        viewModel.remainTime.value = remain
        //TODO SEND MESSAGE
        Timer().schedule(object : TimerTask() {
            override fun run() {
                remain--
                if (remain < 0) {
                    cancel()
                }
                viewModel.remainTime.postValue(remain)
            }
        }, 1000L, 1000L)
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
        viewModel.remainTime.observe(this, Observer {
            if (null == it) return@Observer
            if (it >= 0) {
                binding.verifyCodeTv.text = "重新发送(${it}S)"
            } else {
                binding.verifyCodeTv.text = getString(R.string.get_verify_code)
                binding.verifyCodeTv.isEnabled = true
            }
        })
    }

    private fun checkLogin() {
        val isEnable =
            !viewModel.username.value.isNullOrBlank() && !viewModel.verifyCode.value.isNullOrBlank() && viewModel.confirmCheck.value == true
        binding.nextTv.alpha = if (isEnable) 1f else 0.55f
        binding.nextTv.isEnabled = isEnable
    }


}