package com.meten.xyh.modules.user.view

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentUserAccountBinding
import com.meten.xyh.modules.order.view.OrderActivity
import com.meten.xyh.modules.recharge.view.RechargeActivity
import com.meten.xyh.modules.user.viewmodel.UserAccountViewModel
import com.meten.xyh.utils.BusinessUtil
import com.meten.xyh.view.dialog.GiftDialog
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.topquality.view.CollectionActivity
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.view.dialog.CommonDialog


/**
 * 我的
 */
class UserAccountFragment : BaseFragment<FragmentUserAccountBinding, UserAccountViewModel>() {

    override val viewModel: UserAccountViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.fragment_user_account
    override val viewModelId: Int
        get() = BR.userAccountViewModel


    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.createUserCl.setOnClickListener((NonDoubleClickListener {
            CreateUserActivity.start(requireContext())
        }))
        binding.changeAccountCl.setOnClickListener((NonDoubleClickListener {
            ChangeUserActivity.start(requireContext())
        }))
        binding.giftCl.setOnClickListener((NonDoubleClickListener {
            showGiftDialog()
        }))
        binding.accountOrderCl.setOnClickListener((NonDoubleClickListener {
            OrderActivity.start(requireContext())
        }))
        binding.accountRemarkCl.setOnClickListener((NonDoubleClickListener {
            CollectionActivity.start(requireContext())
        }))
        binding.accountSupportCl.setOnClickListener((NonDoubleClickListener {
            BusinessUtil.callPhone(requireContext())
        }))
        binding.rechargeIv.setOnClickListener(NonDoubleClickListener {
            RechargeActivity.start(requireContext())
        })
        binding.accountBg.setOnClickListener(NonDoubleClickListener {
            UserInfoActivity.start(requireContext())
        })
    }

    private fun showGiftDialog() {
        GiftDialog(requireContext()).apply {
            onClick = {
                viewModel.verifyGiftCode(it)
            }
        }.show()

    }

    fun showGiftSuccessDialog(courseName: String) {
        CommonDialog(requireContext()).apply {
            contentText = "课程：${courseName}兑换成功，请到首页-大师课程，查看\n"
            confirmButtonText = "确定"
        }.show()
    }

    override fun initViewObserver() {
        viewModel.giftSuccess.observe(this, Observer {
            if (it.isNullOrBlank()) {
                showGiftSuccessDialog(it)
            }
        })
    }

}