package com.meten.xyh.modules.recharge.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentRechargeHistoryDetailBinding
import com.meten.xyh.modules.recharge.viewmodel.RechargeHistoryDetailViewModel
import com.meten.xyh.modules.recharge.viewmodel.RechargeHistoryViewModel
import com.meten.xyh.utils.BusinessUtil
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 账单详情
 */
class RechargeHistoryDetailFragment(val id: String) :
    BaseFragment<FragmentRechargeHistoryDetailBinding, RechargeHistoryDetailViewModel>() {

    override val viewModel: RechargeHistoryDetailViewModel by viewModels {
        BaseShareModelFactory()
    }

    val rechargeHistoryViewModel: RechargeHistoryViewModel by activityViewModels {
        BaseShareModelFactory()
    }


    override val layoutId: Int
        get() = R.layout.fragment_recharge_history_detail
    override val viewModelId: Int
        get() = BR.loginViewModel


    override fun initView() {
        binding.header.title.text = "账单详情"
        viewModel.item.value = rechargeHistoryViewModel.rechargeHistoryItems.firstOrNull { it.id == id }
        initListener()
    }

    private fun initListener() {
        binding.header.back.setOnClickListener(NonDoubleClickListener {
            finish()
        })
        binding.supportPhoneTv.setOnClickListener(NonDoubleClickListener {
            BusinessUtil.callPhone(requireContext())
        })
    }


    override fun initViewObserver() {
    }


}