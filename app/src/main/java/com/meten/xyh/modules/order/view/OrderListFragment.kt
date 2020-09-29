package com.meten.xyh.modules.order.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.FragmentOrderListBinding
import com.meten.xyh.enumeration.OrderState
import com.meten.xyh.modules.order.adapter.OrderAdapter
import com.meten.xyh.modules.order.viewmodel.OrderListViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 订单列表
 */
class OrderListFragment : BaseFragment<FragmentOrderListBinding, OrderListViewModel>() {

    companion object {
        fun newInstance(state: OrderState?): OrderListFragment {
            val f = OrderListFragment()
            Bundle().apply {
                state?.let {
                    putString(IntentKey.ORDER_STATE, it.text)
                }
                f.arguments = this
            }
            return f
        }
    }

    override val viewModel: OrderListViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter(
            data = viewModel.orders
        )
    }

    override val layoutId: Int
        get() = R.layout.fragment_order_list
    override val viewModelId: Int
        get() = BR.orderListViewModel


    override fun initParams() {
        super.initParams()
        arguments?.getString(IntentKey.ORDER_STATE)?.let {
            try {
                viewModel.state = OrderState.valueOf(it)
            } catch (e: Exception) {
            }
        }
        viewModel.loadData()
    }

    override fun initView() {
        viewModel.loadData()
        initOrders()
        initListener()
    }


    fun initOrders() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                requireContext(),
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            orderAdapter.setOnItemClickListener { adapter, view, position ->
                val id = orderAdapter.data[position].orderId
                //TODO
            }
            adapter = orderAdapter
        }
    }

    private fun initListener() {
        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.loadOrders {
                    binding.srl.finishLoadMore()
                }

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel.loadOrders("0") {
                    binding.srl.finishRefresh()
                }

            }
        })

    }

    override fun initViewObserver() {
    }


}