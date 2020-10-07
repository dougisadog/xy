package com.meten.xyh.modules.recharge.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.doug.paylib.util.*
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityRechargeBinding
import com.meten.xyh.enumeration.PayType
import com.meten.xyh.modules.recharge.adapter.RechargeAdapter
import com.meten.xyh.modules.recharge.viewmodel.RechargeViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.view.NonDoubleClickListener
import com.shuange.lesson.view.dialog.CommonDialog
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

    override var fragmentContainerId: Int? = com.shuange.lesson.R.id.fragmentContainerFl


    override fun initView() {
        binding.header.title.text = "账户充值"
        initListener()
        initRechargeItem()
    }

    private fun initListener() {
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            val callback = object : PayCallback {
                override fun onSuccess() {
                }

                override fun onFailed(error: String) {
                }
            }
            when (viewModel.payType.value) {
                PayType.WECHAT -> {
                    //TODO
                    val request = WepayRequest()
                    WepayManager.getInstance().pay(request, callback)
                }
                PayType.ALIPAY -> {
                    //TODO
                    val request = AlipayRequest()
                    AlipayManager.getInstance().pay(this, request, callback)
                }
            }

        })
        binding.changePageTypeLl.setOnClickListener(NonDoubleClickListener {
            showFragment(RechargePayTypeFragment())
        })
        binding.header.back.setOnClickListener {
            finish()
        }
    }

    fun initRechargeItem() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(
                this@RechargeActivity,
                2
            )
            rechargeAdapter.setOnItemClickListener { adapter, view, position ->
                rechargeAdapter.data.forEachIndexed { index, rechargeBean ->
                    rechargeBean.isSelected = index == position
                }
                rechargeAdapter.notifyDataSetChanged()
            }
            isNestedScrollingEnabled = false
            adapter = rechargeAdapter
        }
    }

    fun showSuccessDialog() {
        CommonDialog(this).apply {
            contentText = "充值成功"
            confirmButtonText = "确定"
        }.show()
    }

    fun showFailedDialog() {
        CommonDialog(this).apply {
            contentText = "充值失败,若您已经付款,请联系客服"
            confirmButtonText = "确定"
        }.show()
    }

    override fun initViewObserver() {
    }


}