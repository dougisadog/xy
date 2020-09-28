package com.meten.xyh.modules.recharge.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityRechargeBinding
import com.meten.xyh.enumeration.PayType
import com.meten.xyh.modules.recharge.adapter.RechargeAdapter
import com.meten.xyh.modules.recharge.viewmodel.RechargeViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.NonDoubleClickListener
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 我的账户
 */
class RechargeActivity : BaseActivity<ActivityRechargeBinding, RechargeViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, RechargeActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: RechargeViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val rechargeAdapter: RechargeAdapter by lazy {
        RechargeAdapter(
            data = viewModel.rechargeItems
        )
    }


    override val layoutId: Int
        get() = R.layout.activity_recharge
    override val viewModelId: Int
        get() = BR.loginViewModel


    override fun initView() {
        binding.header.title.text = "我的账户"
        initListener()
        initRechargeItem()
    }

    private fun initListener() {
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            when (viewModel.payType.value) {
                PayType.WX-> {

                }
                PayType.ALIPAY -> {

                }
            }

        })
        binding.changePageTypeLl.setOnClickListener(NonDoubleClickListener {
            showFragment(RechargePayTypeFragment())
        })
    }

    fun initRechargeItem() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(
                this@RechargeActivity,
                2
            )
            rechargeAdapter.setOnItemClickListener { adapter, view, position ->
                val rmbValue = rechargeAdapter.data[position].rmbValue
                ToastUtil.show("item click  rmbValue:${rmbValue}")
            }
            isNestedScrollingEnabled = false
            adapter = rechargeAdapter
        }
    }


    override fun initViewObserver() {
    }


}