package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivityChangePasswordBinding
import com.meten.xyh.modules.user.viewmodel.ChangePwdViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.view.dialog.CommonDialog
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 修改密码
 */
class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding, ChangePwdViewModel>() {

    companion object {
        fun start(context: Context, verified: Boolean) {
            val i = Intent(context, ChangePasswordActivity::class.java)
            i.putExtra(IntentKey.CHANGE_PWD_VERIFIED_KEY, verified)
            context.startActivity(i)
        }
    }

    override val viewModel: ChangePwdViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_change_password
    override val viewModelId: Int
        get() = BR.changePwdViewModel

    override fun initParams() {
        super.initParams()
        viewModel.verified.value = intent.getBooleanExtra(IntentKey.CHANGE_PWD_VERIFIED_KEY, false)
    }

    override fun initView() {
        if (viewModel.verified.value == true) {
            binding.header.title.text = "设置密码"
            binding.forgetPwdTv.visibility = View.GONE
        } else {
            binding.header.title.text = "修改密码"
            binding.forgetPwdTv.visibility = View.VISIBLE
        }
        binding.header.rightBtn.text = "保存"
        binding.header.rightBtn.visibility = View.VISIBLE
        initListener()
    }


    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
        binding.header.rightBtn.setOnClickListener {
            viewModel.changePwd()
        }
        binding.forgetPwdTv.setOnClickListener(NonDoubleClickListener {
            CommonDialog(this).apply {
                contentText = "您的账号现在已经绑定手机号，可以通过短信验证码重置微信密码，是否发送短信到${LessonDataCache.account?.phone}？"
                cancelButtonText = "取消"
                confirmButtonText = "确认"
                onClick = {
                    SendVerifyCodeActivity.start(this@ChangePasswordActivity)
                }
            }.show()
        })
    }


    override fun initViewObserver() {
    }

}