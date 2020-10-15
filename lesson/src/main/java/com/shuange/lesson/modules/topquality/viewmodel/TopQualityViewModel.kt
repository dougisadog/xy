package com.shuange.lesson.modules.topquality.viewmodel

import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.base.viewmodel.BaseViewModel

class TopQualityViewModel : BaseViewModel() {

    val pager = mutableListOf<Pair<String, String>>()

    fun loadData() {
    }

    fun loadTypes(onSuccess: EmptyTask) {
        val source = LessonDataCache.shortVideoTypes.mapIndexed { index, pairLessonType ->
            Pair(pairLessonType.value ?: "", pairLessonType.name ?: "")
        }
        pager.addAll(source)
        onSuccess?.invoke()
    }

}