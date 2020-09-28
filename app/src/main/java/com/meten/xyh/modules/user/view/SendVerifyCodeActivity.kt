package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivitySendVerifyCodeBinding
import com.meten.xyh.modules.user.viewmodel.SendVerifyCodeViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 填写验证码
 */
class SendVerifyCodeActivity :
    BaseActivity<ActivitySendVerifyCodeBinding, SendVerifyCodeViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, SendVerifyCodeActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: SendVerifyCodeViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_send_verify_code
    override val viewModelId: Int
        get() = BR.sendVerifyCodeViewModel


    override fun initView() {
        binding.header.title.text = "填写验证码"
        initListener()
    }


    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
        binding.nextTv.setOnClickListener {
            viewModel.verifyCode {
                ChangePasswordActivity.start(this, true)
            }
        }
    }


    override fun initViewObserver() {
    }

}