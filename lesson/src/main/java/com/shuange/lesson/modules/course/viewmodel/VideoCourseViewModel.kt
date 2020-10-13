package com.shuange.lesson.modules.course.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.course.bean.CourseLessonItem
import com.shuange.lesson.modules.course.bean.DraggingCourseBean
import com.shuange.lesson.modules.media.bean.VideoData
import com.shuange.lesson.modules.topquality.bean.CourseBean
import com.shuange.lesson.service.api.LessonPackagesDetailApi
import com.shuange.lesson.service.api.PurchaseRecordsApi
import com.shuange.lesson.service.api.base.suspendExecute

open class VideoCourseViewModel : BaseViewModel() {

    var courseBean: CourseBean? = null

    var mediaData = MutableLiveData<VideoData>()

    var content = MutableLiveData<String>()
    var courses = mutableListOf<CourseLessonItem>()
    var draggingCourses = ObservableArrayList<DraggingCourseBean>()


    val courseRefresh = MutableLiveData<Boolean>()

    val valueNotEnough = MutableLiveData<Boolean>()

    //购买lessonId
    val buyCourse = MutableLiveData<String>()


    fun resetMedia(position: Int) {
        if (courses.size > position) {
            courses[position].sourceUrl?.let {
                mediaData.value =
                    VideoData().apply { setVideo(it) }
            }
        }
    }

    fun loadData() {
        val courseBean = courseBean ?: return
        val lessonPackageId = courseBean.courseId ?: return
        startBindLaunch {
            val suspendResult = LessonPackagesDetailApi(lessonPackageId).suspendExecute()
            suspendResult.getResponse()?.body?.let {
                val source = it.lessons
                source.sortedBy { it.sortNo }
                val needResetCourseSource = courses.size == 0
                source.forEachIndexed { index, lesson ->
                    //课程拉取一次后不再刷新
                    if (needResetCourseSource) {
                        courses.add(CourseLessonItem().apply {
                            setLesson(lesson)
                        })
                    }
                    draggingCourses.clear()
                    draggingCourses.add(DraggingCourseBean().apply {
                        this.title = lesson.name
                        this.isFree =
                            index == 0 || courseBean.freeType == CourseBean.FREE_TYPE_ORANGE
                    })
                }
            }
            suspendResult.exception
        }
//        testData()
    }

    fun buyCourse() {
        val xyPrice = courseBean?.price ?: return
        val xyBalance = LessonDataCache.accountData().xyBalance
        if (xyBalance < xyPrice) {
            valueNotEnough.value = true
            return
        }
        requestBuy(xyPrice)
    }

    fun requestBuy(price: Int) {
        val lessonPackageId = courseBean?.courseId ?: return
        startBindLaunch {
            val suspendResponse = PurchaseRecordsApi(lessonPackageId).suspendExecute()
            suspendResponse.getResponse()?.body?.let {
                //TODO 余额不足
                if (false) {
                    valueNotEnough.value = true
                } else {
                    //TODO 购买成功拉取服务器 还是直接本地强制解锁？
                    courseRefresh.value = true
                }
            }
            suspendResponse.exception
        }

    }

    fun testData() {
        val url = "http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4"
        mediaData.value =
            VideoData().apply { setVideo(url) }
        content.value = "adasdasd123asdasdasdasd123sasd"
        for (i in 0 until 6) {
            courses.add(CourseLessonItem().apply {
                name = "topQuality$i"
                this.sourceUrl = url
            })
        }
    }

}