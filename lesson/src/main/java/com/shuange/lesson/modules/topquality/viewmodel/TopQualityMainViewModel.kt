package com.shuange.lesson.modules.topquality.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.course.bean.CourseInfoItem
import com.shuange.lesson.modules.topquality.bean.CourseBean
import com.shuange.lesson.service.api.LessonPackagesApi
import com.shuange.lesson.service.api.LessonPackagesRecommendApi
import com.shuange.lesson.service.api.base.suspendExecute
import com.shuange.lesson.service.response.LessonPackagesResponse
import com.shuange.lesson.service.response.base.SuspendResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class TopQualityMainViewModel : BaseViewModel() {

    //精品课程 和主页相同不截取
    var topQualityItems = ObservableArrayList<CourseBean>()

    val courses = ObservableArrayList<CourseInfoItem>()

    val pagerData = mutableListOf<CourseBean>()

    fun loadData() {
        startBindLaunch {
            var exception: Exception? = null
            val tasks = arrayListOf(
                async { LessonPackagesRecommendApi().suspendExecute() },
                async {
                    LessonPackagesApi().apply {
                        addPageParam("0",  page = 1, size = 10)
                    }.suspendExecute()
                }

            )
            val results = tasks.awaitAll()
            val suspendLessonPackagesRecommendResult =
                (results[0] as? SuspendResponse<LessonPackagesResponse>)
            suspendLessonPackagesRecommendResult?.getResponse()?.body?.let {
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
            val suspendLessonPackagesResult =
                (results[0] as? SuspendResponse<LessonPackagesResponse>)
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
                courses.clear()
                courses.addAll(converted)
            }
            exception
        }
        initTestData()
    }

    fun initTestData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 3) {

            topQualityItems.add(CourseBean().apply {
                title = "topQuality$i"
                content = "topQuality content$i"
                image = baseImg
                freeType =
                    if (i == 0) null else if (i == 1) CourseBean.FREE_TYPE_GREEN else CourseBean.FREE_TYPE_ORANGE
            })
            pagerData.add(CourseBean().apply {
                title = "news$i"
                content = "news content$i"
                image = baseImg
            })
        }

        for (i in 0 until 4) {
            courses.add(CourseInfoItem().apply {
                title = "初级课程$i"
                content = "4.学问位置》核心课程A"
                image =
                    "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
            })
        }

    }
}