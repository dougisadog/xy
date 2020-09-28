package com.meten.xyh.modules.recharge.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentRechargePayTypeBinding
import com.meten.xyh.enumeration.PayType
import com.meten.xyh.modules.recharge.viewmodel.RechargePayTypeViewModel
import com.meten.xyh.modules.recharge.viewmodel.RechargeViewModel
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 充值
 */
class RechargePayTypeFragment :
    BaseFragment<FragmentRechargePayTypeBinding, RechargePayTypeViewModel>() {

    override val viewModel: RechargePayTypeViewModel by viewModels {
        BaseShareModelFactory()
    }

    val rechargeViewModel: RechargeViewModel by activityViewModels {
        BaseShareModelFactory()
    }


    override val layoutId: Int
        get() = R.layout.fragment_recharge_pay_type
    override val viewModelId: Int
        get() = BR.rechargePayTypeViewModel


    override fun initView() {
        binding.header.title.text = "充值"
        viewModel.payType.value = rechargeViewModel.payType.value
        initListener()
    }

    private fun initListener() {
        binding.header.back.setOnClickListener(NonDoubleClickListener {
            rechargeViewModel.payType.value = viewModel.payType.value
            finish()
        })
        binding.alipay.setOnClickListener {
            viewModel.payType.value = PayType.ALIPAY
        }
        binding.wepay.setOnClickListener {
            viewModel.payType.value = PayType.WX
        }
    }


    override fun initViewObserver() {
        viewModel.payType.observe(this, Observer {
            when (it) {
                PayType.ALIPAY -> {
                    binding.alipayCheck.isSelected = true
                    binding.wepayCheck.isSelected = false
                }
                PayType.WX -> {
                    binding.alipayCheck.isSelected = false
                    binding.wepayCheck.isSelected = true
                }
                else -> {

                }
            }
        })
    }


}