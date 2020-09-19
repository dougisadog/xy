package com.shuange.lesson.modules.lesson.viewmodel

import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.lesson.bean.LessonBean

open class BaseLessonViewModel : BaseViewModel() {

    val done = MutableLiveData<Boolean>(true)

    var lessonBean : LessonBean? = null


}