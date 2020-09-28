package com.meten.xyh.modules.recharge.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityRechargeHistoryBinding
import com.meten.xyh.modules.recharge.adapter.RechargeHistoryAdapter
import com.meten.xyh.modules.recharge.viewmodel.RechargeHistoryViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 我的账户
 */
class RechargeHistoryActivity :
    BaseActivity<ActivityRechargeHistoryBinding, RechargeHistoryViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, RechargeHistoryActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: RechargeHistoryViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val rechargeHistoryAdapter: RechargeHistoryAdapter by lazy {
        RechargeHistoryAdapter(
            layout = R.layout.layout_recharge_history_item,
            data = viewModel.rechargeHistoryItems
        )
    }

    override val layoutId: Int
        get() = R.layout.activity_recharge_history
    override val viewModelId: Int
        get() = BR.rechargeHistoryViewModel


    override fun initView() {
        binding.header.title.text = "我的账户"
        initListener()
        initRechargeItem()
    }

    fun initRechargeItem() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@RechargeHistoryActivity,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            rechargeHistoryAdapter.setOnItemClickListener { adapter, view, position ->
                val id = rechargeHistoryAdapter.data[position].id
                showFragment(RechargeHistoryDetailFragment(id))
            }
            adapter = rechargeHistoryAdapter
        }
    }

    private fun initListener() {
        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.loadRechargeHistories {
                    binding.srl.finishLoadMore()
                }

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel.loadRechargeHistories("0") {
                    binding.srl.finishRefresh()
                }

            }
        })
    }


    override fun initViewObserver() {
    }


}