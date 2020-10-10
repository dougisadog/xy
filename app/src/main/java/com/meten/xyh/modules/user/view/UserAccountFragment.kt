package com.meten.xyh.modules.user.view

import androidx.fragment.app.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentUserAccountBinding
import com.meten.xyh.modules.order.view.OrderActivity
import com.meten.xyh.modules.recharge.view.RechargeActivity
import com.meten.xyh.modules.user.viewmodel.UserAccountViewModel
import com.meten.xyh.utils.BusinessUtil
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.topquality.view.CollectionActivity
import com.shuange.lesson.view.NonDoubleClickListener


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
//            UserInfoActivity.start(requireContext())
            CreateUserActivity.start(requireContext())
        }))
        binding.changeAccountCl.setOnClickListener((NonDoubleClickListener {
            ChangeUserActivity.start(requireContext())
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

    override fun initViewObserver() {
    }

}