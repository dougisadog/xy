package com.meten.xyh.modules.news.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentNewsListBinding
import com.meten.xyh.modules.discovery.adapter.BaseItemAdapter
import com.meten.xyh.modules.news.viewmodel.NewListViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuange.lesson.BR
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.ToastUtil

/**
 * 英语资讯
 */
class NewsListFragment :
    BaseFragment<FragmentNewsListBinding, NewListViewModel>() {

    companion object {
        fun newInstance(): NewsListFragment {
            val f = NewsListFragment()
            Bundle().apply {
                f.arguments = this
            }
            return f
        }
    }

    override val viewModel: NewListViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.fragment_news_list
    override val viewModelId: Int
        get() = BR.newListViewModel

    private val newsAdapter: BaseItemAdapter by lazy {
        BaseItemAdapter(
            layout = R.layout.layout_base_item,
            data = viewModel.newsItems
        )
    }

    override fun initView() {
        viewModel.loadNews("0")
        initListener()
        initNews()
    }

    fun initNews() {
        with(binding.newsRv) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            newsAdapter.setOnItemClickListener { adapter, view, position ->
                ToastUtil.show("item click  teacher:${newsAdapter.data[position].title}")
            }
            isNestedScrollingEnabled = false
            adapter = newsAdapter
        }
    }

    private fun initListener() {
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

    fun search(text: String) {
        viewModel.searchText.value = text
        viewModel.loadNews("0")
    }


}