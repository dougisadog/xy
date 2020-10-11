package com.meten.xyh.modules.search.view

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivitySearchBaseBinding
import com.meten.xyh.enumeration.SearchType
import com.meten.xyh.modules.search.viewmodel.SearchBaseViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.utils.extension.setOnSearchListener
import kotlin.reflect.KClass


/**
 * TODO 弃用 搜索
 */
abstract class SearchBaseActivity<VM : SearchBaseViewModel> :
    BaseActivity<ActivitySearchBaseBinding, VM>() {

    companion object {
        fun start(context: Context, searchType: SearchType) {
            val targetClass: KClass<*> = when (searchType) {
                SearchType.TEACHER -> {
                    SearchTeacherActivity::class
                }
                SearchType.COURSE -> {
                    SearchCourseActivity::class
                }
                SearchType.NEWS -> {
                    SearchNewsActivity::class
                }
            }
            targetClass.let {
                val i = Intent(context, it.java)
                context.startActivity(i)
            }

        }
    }


    override val layoutId: Int
        get() = R.layout.activity_search_base
    override val viewModelId: Int
        get() = BR.loginViewModel


    override fun initParams() {
        super.initParams()
        intent.getStringExtra(IntentKey.SEARCH_KEY)?.let {
            viewModel.searchText.value = it
            search(it)
        }
    }

    fun initTeachers() {
        with(binding.rv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@SearchBaseActivity,
                RecyclerView.VERTICAL,
                false
            )
            val searchAdapter = getSearchAdapter()
            searchAdapter.setOnItemClickListener { adapter, view, position ->
                val id = viewModel.searchData[position].getItemId()
                onItemClick(id)
            }
            adapter = getSearchAdapter()
            adapter?.let {
                it.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        super.onChanged()
                        viewModel.isEmpty.value = it.itemCount == 0
                    }
                })
            }
        }
    }

    abstract fun getSearchAdapter(): BaseListAdapter<*>

    abstract fun onItemClick(id: String)

    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.searchEt.setOnSearchListener {
            search(it.trim())
        }
        binding.cancelTv.setOnClickListener {
            viewModel.searchText.value = ""
        }
        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                val lastIndex = viewModel.searchData.lastOrNull()?.getItemId() ?: "0"
                viewModel.loadData(lastIndex) {
                    binding.srl.finishLoadMore()
                }

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel.loadData("0") {
                    binding.srl.finishRefresh()
                }

            }
        })
    }

    fun search(text: String) {
        viewModel.loadData()
    }


    override fun initViewObserver() {
    }


}