package com.shuange.lesson.modules.lesson.viewmodel

import androidx.lifecycle.MutableLiveData

open class RecordingLessonViewModel : NormalLessonViewModel() {

    val recordDone = MutableLiveData<Boolean>()

}