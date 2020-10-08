package com.shuange.lesson.modules.topquality.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.enumeration.WheelType
import com.shuange.lesson.modules.course.bean.CourseInfoItem
import com.shuange.lesson.modules.topquality.bean.CourseBean
import com.shuange.lesson.service.api.LessonPackagesApi
import com.shuange.lesson.service.api.LessonPackagesRecommendApi
import com.shuange.lesson.service.api.WheelsApi
import com.shuange.lesson.service.api.base.suspendExecute

class TopQualityMainViewModel : BaseViewModel() {

    //精品课程 和主页相同不截取
    var topQualityItems = ObservableArrayList<CourseBean>()

    val courses = ObservableArrayList<CourseInfoItem>()

    val wheels = mutableListOf<BaseItemBean>()

    fun loadData() {
        loadLessonPackagesRecommend()
        loadLessonPackages()
        loadWheels()
//        initTestData()
    }

    /**
     *轮播
     */
    fun loadWheels() {
        startBindLaunch {
            val suspendResult = WheelsApi(WheelType.LESSON).suspendExecute()
            suspendResult.let {
                it.getResponse()?.body?.forEach {
                    wheels.add(
                        BaseItemBean().apply { setWheel(it) })
                }
            }
            suspendResult.exception
        }
    }

    fun loadLessonPackagesRecommend() {
        startBindLaunch {
            val suspendLessonPackagesRecommendResult =
                LessonPackagesRecommendApi().suspendExecute()
            suspendLessonPackagesRecommendResult.getResponse()?.body?.let {
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
            suspendLessonPackagesRecommendResult.exception
        }
    }

    fun loadLessonPackages() {
        startBindLaunch {
            val suspendLessonPackagesResult =
                LessonPackagesApi().apply {
                    addPageParam("0", page = 1, size = 10)
                }.suspendExecute()
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
            suspendLessonPackagesResult.exception
        }
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
            wheels.add(CourseBean().apply {
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