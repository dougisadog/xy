package com.meten.xyh.modules.search.view

import androidx.activity.viewModels
import com.meten.xyh.R
import com.meten.xyh.modules.discovery.adapter.BaseItemAdapter
import com.meten.xyh.modules.search.viewmodel.SearchNewsViewModel
import com.shuange.lesson.base.adapter.BaseListAdapter
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 搜索新闻
 */
class SearchNewsActivity :
    SearchBaseActivity<SearchNewsViewModel>() {

    private val newsAdapter: BaseItemAdapter by lazy {
        BaseItemAdapter(
            layout = R.layout.layout_base_item,
            data = viewModel.newsItems
        )
    }

    override val viewModel: SearchNewsViewModel by viewModels {
        BaseShareModelFactory()
    }

    override fun getSearchAdapter(): BaseListAdapter<*> {
        return newsAdapter
    }

    override fun onItemClick(id: String) {
//        NewsDetail
    }


}