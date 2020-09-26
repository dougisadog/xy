package com.meten.xyh.modules.search.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.modules.news.bean.NewsBean
import com.meten.xyh.service.api.ArticlesApi
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.PagerItem
import com.shuange.lesson.service.api.base.suspendExecute

class SearchNewsViewModel : SearchBaseViewModel() {

    val newsItems = ObservableArrayList<BaseItemBean>()

    override val searchData: MutableList<PagerItem>
        get() {
            return newsItems.toMutableList()
        }

    override fun loadData(lastIndex: String, onFinished: EmptyTask) {
        if (lastIndex == "0") {
            newsItems.clear()
        }
        startBindLaunch(onFinish = onFinished) {
            val suspendArticlesResult = ArticlesApi().suspendExecute()
            suspendArticlesResult.getResponse()?.body?.forEach {
                newsItems.add(NewsBean().apply {
                    title = it.title
                    content = it.subTitle
                    image = it.imageUrl
                })
            }
            suspendArticlesResult.exception
        }


    }

}