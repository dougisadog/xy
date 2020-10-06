package com.meten.xyh.modules.news.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.meten.xyh.modules.news.bean.NewsBean
import com.meten.xyh.service.api.ArticlesApi
import com.meten.xyh.service.api.ArticlesWheelsApi
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute

class NewListViewModel : BaseViewModel() {

    var searchText = MutableLiveData<String>()

    var newsItems = ObservableArrayList<BaseItemBean>()

    var wheels = mutableListOf<String>()

    fun loadData() {

    }

    fun loadNews(
        startId: String = newsItems.lastOrNull()?.id ?: "0",
        page: Int = 50,
        onFinished: EmptyTask = null
    ) {
        startBindLaunch {
            val exception: Exception? = null
            val suspendResult = ArticlesApi().suspendExecute()
            suspendResult.getResponse()?.body?.forEach {
                newsItems.add(NewsBean().apply {
                    title = it.title
                    content = it.subTitle
                    image = it.imageUrl
                })

            }
            if (null == exception) {
                suspendResult.exception
            }
            val suspendWheelsResult = ArticlesWheelsApi().suspendExecute()
            suspendWheelsResult.getResponse()?.body?.forEach {
                wheels.add(it.imageUrl)

            }
            if (null == exception) {
                suspendWheelsResult.exception
            }
            exception
        }
        initTestData()
    }

    fun initTestData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 3) {
            newsItems.add(NewsBean().apply {
                title = "news$i"
                content = "news content$i"
                image = baseImg
            })
            val link =
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
            wheels.add(link)
        }
    }
}