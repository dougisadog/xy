package com.meten.xyh.modules.discovery.viewmodel

import androidx.databinding.ObservableArrayList
import com.meten.xyh.modules.discovery.bean.StreamLessonBean
import com.meten.xyh.modules.news.bean.NewsBean
import com.meten.xyh.modules.teacher.bean.TeacherBean
import com.meten.xyh.service.api.ArticlesRecommendApi
import com.meten.xyh.service.response.ArticlesResponse
import com.meten.xyh.service.response.TeachersResponse
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.course.bean.CourseInfoItem
import com.shuange.lesson.modules.topquality.bean.TopQualityCourseBean
import com.shuange.lesson.service.api.LessonPackagesRecommendApi
import com.shuange.lesson.service.api.base.suspendExecute
import com.shuange.lesson.service.response.LessonPackagesResponse
import com.shuange.lesson.service.response.base.SuspendResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

open class DiscoveryViewModel : BaseViewModel() {

    var streamLessons = ObservableArrayList<StreamLessonBean>()

    var teachers = ObservableArrayList<TeacherBean>()

    var topQualityItems = ObservableArrayList<TopQualityCourseBean>()

    var newsItems = ObservableArrayList<BaseItemBean>()

    fun loadData() {
        startBindLaunch {
            var exception: Exception? = null
            val tasks = arrayListOf(async { ArticlesRecommendApi().suspendExecute() },
                async { ArticlesRecommendApi().suspendExecute() },
                async { LessonPackagesRecommendApi().suspendExecute() }
            )
            val results = tasks.awaitAll()
            val suspendArticlesResult = (results[0] as? SuspendResponse<ArticlesResponse>)
            suspendArticlesResult?.getResponse()?.body?.forEach {
                newsItems.add(NewsBean().apply {
                    title = it.title
                    content = it.subTitle
                    image = it.imageUrl
                })
            }
            if (null == exception) {
                exception = suspendArticlesResult?.exception
            }
            val suspendTeacherResult = (results[1] as? SuspendResponse<TeachersResponse>)
            suspendTeacherResult?.getResponse()?.body?.forEach {
                teachers.add(TeacherBean().apply {
                    name = it.name
                    introduction = it.description
                    image = it.imageUrl
                    subTitle = it.info
                })
            }
            if (null == exception) {
                exception = suspendTeacherResult?.exception
            }
            val suspendLessonPackagesResult = (results[2] as? SuspendResponse<LessonPackagesResponse>)
            suspendLessonPackagesResult?.getResponse()?.body?.let {
                val arr = it
                arr.sortedBy {
                    it.sortNo
                }
                val converted = arr.map { lp ->
                    CourseInfoItem().apply {
                        setLessonPackages(lp)
                    }
                }
                topQualityItems.clear()
                topQualityItems.addAll(converted)
            }
            if (null == exception) {
                exception = suspendTeacherResult?.exception
            }
            exception
        }
        initTestData()
    }

    fun initTestData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 3) {
            streamLessons.add(StreamLessonBean().apply {
                title = "stream$i"
                teacher = "stream teacher$i"
                snapshot = baseImg

            })
            teachers.add(TeacherBean().apply {
                name = "name$i"
                introduction = "introduction$i"
                image = baseImg
            })

            topQualityItems.add(TopQualityCourseBean().apply {
                title = "topQuality$i"
                content = "topQuality content$i"
                image = baseImg
                freeType =
                    if (i == 0) null else if (i == 1) TopQualityCourseBean.FREE_TYPE_GREEN else TopQualityCourseBean.FREE_TYPE_ORANGE
            })
            newsItems.add(NewsBean().apply {
                title = "news$i"
                content = "news content$i"
                image = baseImg
            })
        }
    }

}