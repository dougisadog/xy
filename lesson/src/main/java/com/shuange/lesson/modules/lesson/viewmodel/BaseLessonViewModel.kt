package com.shuange.lesson.modules.lesson.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.QuestionBean
import com.shuange.lesson.service.api.UserLessonRecordApi
import com.shuange.lesson.service.api.base.suspendExecute
import com.shuange.lesson.service.request.UserLessonRecordRequest

open class BaseLessonViewModel : BaseViewModel() {

    val done = MutableLiveData<Boolean>(true)

    var questionBean: QuestionBean? = null

    fun saveLessonProcess(index: Int, answer: String, score: Int) {
        val lessonBean = questionBean ?: return
        startBindLaunch {
            val request = UserLessonRecordRequest(
                answer = answer,
                lessonModuleId = lessonBean.moduleId,
                lessonId = lessonBean.lessonId,
                progressIndex = index,
                progressTime = 0,
                questionId = lessonBean.questionsId,
                score = score
            )
            val suspendResult = UserLessonRecordApi(request).suspendExecute()
            suspendResult.exception
        }
    }
}