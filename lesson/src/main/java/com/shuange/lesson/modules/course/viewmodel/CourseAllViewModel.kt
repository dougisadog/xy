package com.shuange.lesson.modules.course.viewmodel

import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.LessonDataCache
import com.shuange.lesson.base.viewmodel.BaseViewModel

class CourseAllViewModel : BaseViewModel() {

    var defaultTypeId: String? = null

    val pager = mutableListOf<Pair<String, String>>()

    fun getDefaultPageIndex(): Int {
        val typeId = defaultTypeId ?: return 0
        val target = pager.map { it.first }.indexOf(typeId)
        if (target == -1) {
            return 0
        }
        return target
    }

    fun loadData() {

    }

    fun loadTypes(onSuccess: EmptyTask) {
        val source = LessonDataCache.types.mapIndexed { index, pairLessonType ->
            Pair(pairLessonType.value ?: "", pairLessonType.name ?: "")
        }
        pager.addAll(source)
        onSuccess?.invoke()
    }

}