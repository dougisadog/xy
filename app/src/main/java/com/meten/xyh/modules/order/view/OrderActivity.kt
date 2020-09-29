package com.meten.xyh.modules.order.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.ActivityOrderBinding
import com.meten.xyh.enumeration.OrderState
import com.meten.xyh.modules.order.viewmodel.OrderViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseFragmentAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.extension.bind
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 订单
 */
class OrderActivity : BaseActivity<ActivityOrderBinding, OrderViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, OrderActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: OrderViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_order
    override val viewModelId: Int
        get() = BR.orderViewModel

    lateinit var mainAdapter: BaseFragmentAdapter


    override fun initView() {
        binding.header.title.text = "订单"
        viewModel.loadData()
        initListener()
        initViewPager()
        initTabIndicator()
    }

    private fun initTabIndicator() {
//        binding.tabTl.init(binding.vp, viewModel.pager)
        binding.indicators.bind(binding.vp, viewModel.pager)
    }


    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        fragments.add(OrderListFragment.newInstance(OrderState.ALL))
        fragments.add(OrderListFragment.newInstance(OrderState.PENDING))
        fragments.add(OrderListFragment.newInstance(OrderState.FINISHED))
        mainAdapter = BaseFragmentAdapter(this, fragments)
        with(binding.vp) {
            adapter = mainAdapter
        }
    }

    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
    }

    override fun initViewObserver() {
    }


}