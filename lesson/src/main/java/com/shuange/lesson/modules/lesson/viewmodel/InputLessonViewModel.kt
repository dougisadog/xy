package com.shuange.lesson.modules.lesson.viewmodel

import androidx.lifecycle.MutableLiveData

open class InputLessonViewModel : BaseLessonViewModel() {

    var currentInput = MutableLiveData<String>()


}