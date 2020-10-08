package com.meten.xyh.modules.discovery.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.enumeration.WheelType
import com.shuange.lesson.modules.course.bean.CoursePackageItem
import com.shuange.lesson.modules.course.bean.StreamLessonBean
import com.shuange.lesson.modules.news.bean.NewsBean
import com.shuange.lesson.modules.teacher.bean.TeacherBean
import com.shuange.lesson.modules.topquality.bean.CourseBean
import com.shuange.lesson.service.api.ArticlesRecommendApi
import com.shuange.lesson.service.api.LessonPackagesRecommendApi
import com.shuange.lesson.service.api.TeachersRecommendApi
import com.shuange.lesson.service.api.WheelsApi
import com.shuange.lesson.service.api.base.suspendExecute

open class DiscoveryViewModel : BaseViewModel() {

    val wheels = mutableListOf<BaseItemBean>()

    var streamLessons = ObservableArrayList<StreamLessonBean>()

    var teachers = ObservableArrayList<TeacherBean>()

    var topQualityItems = ObservableArrayList<CourseBean>()

    var newsItems = ObservableArrayList<BaseItemBean>()


    fun loadArticles() {
        startBindLaunch {
            val suspendArticlesResult = ArticlesRecommendApi().suspendExecute()
            suspendArticlesResult?.getResponse()?.body?.forEach {
                newsItems.add(NewsBean().apply {
                    title = it.title
                    content = it.subTitle
                    image = it.imageUrl
                })
            }
            suspendArticlesResult.exception
        }
    }

    fun loadCourses() {
        startBindLaunch {
            val suspendLessonPackagesResult = LessonPackagesRecommendApi().suspendExecute()
            suspendLessonPackagesResult.getResponse()?.body?.let {
                val arr = it
                arr.sortedBy {
                    it.sortNo
                }
                val converted = arr.map { lp ->
                    CoursePackageItem().apply {
                        setLessonPackages(lp)
                    }
                }
                topQualityItems.clear()
                topQualityItems.addAll(converted)
            }
            suspendLessonPackagesResult.exception
        }
    }

    fun loadTeachers() {
        startBindLaunch {
            val suspendTeacherResult = TeachersRecommendApi().suspendExecute()
            suspendTeacherResult.getResponse()?.body?.forEach {
                teachers.add(TeacherBean().apply {
                    name = it.name
                    introduction = it.description
                    image = it.imageUrl
                    subTitle = it.info
                })
            }
            suspendTeacherResult.exception
        }
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

    fun loadData() {
        loadArticles()
        loadCourses()
        loadTeachers()
        loadWheels()
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

            topQualityItems.add(CourseBean().apply {
                title = "topQuality$i"
                content = "topQuality content$i"
                image = baseImg
                freeType =
                    if (i == 0) null else if (i == 1) CourseBean.FREE_TYPE_GREEN else CourseBean.FREE_TYPE_ORANGE
            })
            newsItems.add(NewsBean().apply {
                title = "news$i"
                content = "news content$i"
                image = baseImg
            })

            wheels.add(CourseBean().apply {
                title = "news$i"
                content = "news content$i"
                image = baseImg
            })
        }
    }

}