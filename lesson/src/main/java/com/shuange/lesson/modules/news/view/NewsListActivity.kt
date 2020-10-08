package com.shuange.lesson.modules.news.view

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuange.lesson.BR
import com.shuange.lesson.R
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseItemAdapter
import com.shuange.lesson.base.adapter.RecyclePagerAdapter
import com.shuange.lesson.base.adapter.registerRecycleOnPageChangeCallback
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.databinding.ActivityNewsListBinding
import com.shuange.lesson.modules.news.viewmodel.NewListViewModel
import com.shuange.lesson.utils.extension.initAdapter
import com.shuange.lesson.view.NonDoubleClickListener
import corelib.util.DeviceUtils
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * 英语资讯
 */
class NewsListActivity :
    BaseActivity<ActivityNewsListBinding, NewListViewModel>() {

    companion object {
        fun start(context: Context) {
            val i = Intent(context, NewsListActivity::class.java)
            context.startActivity(i)
        }
    }

    override val viewModel: NewListViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_news_list
    override val viewModelId: Int
        get() = BR.newListViewModel

    private val newsAdapter: BaseItemAdapter by lazy {
        BaseItemAdapter(
            layout = R.layout.layout_base_item,
            data = viewModel.newsItems
        )
    }

    override fun initView() {
        binding.header.title.text = "英语资讯"
        viewModel.loadData()
        initListener()
        initViewPager()
        initNews()
    }

    fun initNews() {
        with(binding.newsRv) {
            layoutManager = LinearLayoutManager(this@NewsListActivity, RecyclerView.VERTICAL, false)
            newsAdapter.setOnItemClickListener { adapter, view, position ->
                NewsDetailActivity.start(this@NewsListActivity, newsAdapter.data[position])
            }
            isNestedScrollingEnabled = false
            adapter = newsAdapter
        }
    }

    private fun initViewPager() {
        binding.vp.initAdapter(this, viewModel.wheels)
        binding.vp.setOnClickListener(NonDoubleClickListener {
            val news = viewModel.wheels[binding.vp.currentItem]
            NewsDetailActivity.start(this, news)
        })
        bindIndicatorToViewPager(binding.indicatorContainerLl, binding.vp)
    }

    fun bindIndicatorToViewPager(
        indicatorContainer: LinearLayout,
        viewPager2: ViewPager2,
        selectorRes: Int = R.drawable.selector_indicator
    ) {
        val a = viewPager2.adapter as? RecyclePagerAdapter<*> ?: return
        val size = a.data.size
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val margin = DeviceUtils.toPx(this, 4)
        params.setMargins(margin, 0, margin, 0)
        for (i in 0 until size) {
            val indicator = ImageView(this)
            indicator.setImageResource(selectorRes)
            indicator.layoutParams = params
            indicatorContainer.addView(indicator)
        }
        viewPager2.registerRecycleOnPageChangeCallback { position ->
            for (i in 0 until size) {
                indicatorContainer.getChildAt(i).isSelected = i == position
            }
        }
    }

    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.loadNews {
                    binding.srl.finishLoadMore()
                }

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel.loadNews("0") {
                    binding.srl.finishRefresh()
                }

            }
        })
    }


    override fun initViewObserver() {
    }


}