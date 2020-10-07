package com.shuange.lesson.modules.lesson.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.LessonBean
import com.shuange.lesson.service.api.UserLessonRecordApi
import com.shuange.lesson.service.api.base.suspendExecute
import com.shuange.lesson.service.request.UserLessonRecordRequest

open class BaseLessonViewModel : BaseViewModel() {

    val done = MutableLiveData<Boolean>(true)

    var lessonBean: LessonBean? = null

    fun saveLessonProcess() {
        val lessonBean = lessonBean ?: return
        startBindLaunch {
            val questionId: Int?
            val lessonId: Int?
            try {
                questionId = lessonBean.id.toInt()
                lessonId = lessonBean.lessonId?.toInt()
            } catch (e: Exception) {
                return@startBindLaunch null
            }
            lessonId ?: return@startBindLaunch null
            val request = UserLessonRecordRequest(
//                id = DataCachec
                lessonId = lessonId,
                questionId = questionId,
                score = lessonBean.score
            )
            val suspendResult = UserLessonRecordApi(request).suspendExecute()
            suspendResult.exception
        }
    }
}