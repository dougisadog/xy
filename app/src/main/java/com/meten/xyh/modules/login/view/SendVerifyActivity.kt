package com.meten.xyh.modules.login.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivitySendVerifyBinding
import com.meten.xyh.modules.login.viewmodel.SendVerifyViewModel
import com.meten.xyh.view.CodeEditText
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 发送验证码
 */
class SendVerifyActivity : BaseActivity<ActivitySendVerifyBinding, SendVerifyViewModel>() {

    companion object {
        fun start(context: Context, phone: String) {
            val i = Intent(context, SendVerifyActivity::class.java)
            i.putExtra(IntentKey.PHONE_KEY, phone)
            context.startActivity(i)
        }
    }

    override val viewModel: SendVerifyViewModel by viewModels {
        BaseShareModelFactory()
    }


    override val layoutId: Int
        get() = R.layout.activity_send_verify
    override val viewModelId: Int
        get() = BR.sendVerifyViewModel

    override fun initParams() {
        super.initParams()
        viewModel.username.value = intent.getStringExtra(IntentKey.PHONE_KEY)
    }

    override fun initView() {
        binding.header.title.text = ""
        initListener()
    }

    private fun initListener() {
        binding.header.back.setOnClickListener(NonDoubleClickListener {
            finish()
        })
        binding.verifyInput.setOnTextFinishListener(object : CodeEditText.OnTextFinishListener {
            override fun onTextFinish(text: CharSequence?, length: Int) {
                viewModel.verifyCode.value = text?.toString()
                viewModel.verify()
            }
        })
        binding.resendTv.setOnClickListener(NonDoubleClickListener {
            viewModel.sendMessage()
        })
    }


    override fun initViewObserver() {
        viewModel.verified.observe(this, Observer {
            if (it == true) {
                SetNewPasswordActivity.start(this, viewModel.username.value ?: "")
            }
        })
    }

}