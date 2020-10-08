package com.meten.xyh.modules.news.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.meten.xyh.modules.news.bean.NewsBean
import com.meten.xyh.service.api.ArticlesApi
import com.meten.xyh.service.api.ArticlesWheelsApi
import com.meten.xyh.service.response.ArticlesResponse
import com.meten.xyh.service.response.WheelsResponse
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.service.api.base.suspendExecute
import com.shuange.lesson.service.response.base.SuspendResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class NewListViewModel : BaseViewModel() {

    var searchText = MutableLiveData<String>()

    var newsItems = ObservableArrayList<BaseItemBean>()

    var wheels = mutableListOf<BaseItemBean>()

    fun loadData() {

    }

    fun loadNews(
        startId: String = newsItems.lastOrNull()?.id ?: "0",
        page: Int = 50,
        onFinished: EmptyTask = null
    ) {
        startBindLaunch(onFinish = onFinished) {
            val exception: Exception? = null
            val tasks = arrayListOf(
                async {
                    ArticlesApi().apply { addPageParam(startId) }.suspendExecute()
                },
                async {
                    ArticlesWheelsApi().suspendExecute()
                }
            )
            val suspendResults = tasks.awaitAll()
            (suspendResults[0] as? SuspendResponse<ArticlesResponse>)?.let {
                if (null == exception) {
                    it.exception
                }
                it.getResponse()?.body?.forEach {
                    newsItems.add(NewsBean().apply {
                        id = it.id.toString()
                        title = it.title
                        content = it.subTitle
                        image = it.imageUrl
                    })

                }
            }
            (suspendResults[1] as? SuspendResponse<WheelsResponse>)?.let {
                if (null == exception) {
                    it.exception
                }
                it.getResponse()?.body?.forEach {
                    wheels.add(BaseItemBean(it.title, it.subTitle, it.imageUrl))
                }
            }
            exception
        }
//        initTestData()
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
            wheels.add(BaseItemBean("title$i", "content$i", link))
        }
    }
}