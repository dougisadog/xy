package com.meten.xyh.modules.user.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityUserInfoBinding
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.modules.user.viewmodel.UserInfoViewModel
import com.meten.xyh.modules.usersetting.view.BaseUserSettingActivity
import com.meten.xyh.modules.usersetting.view.InterestActivity
import com.meten.xyh.modules.usersetting.view.SignatureActivity
import com.meten.xyh.view.pop.ChangePhonePop
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 用户信息
 */
class UserInfoActivity : BaseActivity<ActivityUserInfoBinding, UserInfoViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, UserInfoActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: UserInfoViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_user_info
    override val viewModelId: Int
        get() = BR.userInfoViewModel


    override fun initView() {
        binding.header.title.text = "用户信息"
        viewModel.loadData()
        initListener()
    }


    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }

        binding.titleCl.root.setOnClickListener(NonDoubleClickListener {
            SignatureActivity.start(this@UserInfoActivity, UserSettingType.NICKNAME)
        })
        binding.signatureCl.root.setOnClickListener(NonDoubleClickListener {
            SignatureActivity.start(
                this@UserInfoActivity,
                UserSettingType.SIGNATURE
            )
        })
        binding.stageCl.root.setOnClickListener(NonDoubleClickListener {
            BaseUserSettingActivity.start(
                this@UserInfoActivity,
                UserSettingType.STAGE,
                isSetting = true
            )
        })
        binding.interestCl.root.setOnClickListener(NonDoubleClickListener {
            InterestActivity.start(this@UserInfoActivity)
        })
        binding.objectiveCl.root.setOnClickListener(NonDoubleClickListener {
            BaseUserSettingActivity.start(
                this@UserInfoActivity,
                UserSettingType.OBJECTIVE,
                true
            )
        })

        binding.phoneCl.root.setOnClickListener(NonDoubleClickListener {
            ChangePhonePop(this).apply {
                phonePopCallBack = object : ChangePhonePop.PhonePopCallBack {
                    override fun onChange() {
                        ChangePhoneActivity.start(this@UserInfoActivity)
                    }

                    override fun onDelete() {
                        viewModel.deletePhone()
                    }

                }
            }.show(binding.root)
        })

        binding.passwordCl.root.setOnClickListener(NonDoubleClickListener {
            ChangePasswordActivity.start(this@UserInfoActivity, false)
        })

    }


    override fun initViewObserver() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCurrentUser()
    }
}