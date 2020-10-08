package com.meten.xyh.modules.search.viewmodel

import androidx.databinding.ObservableArrayList
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.PagerItem
import com.shuange.lesson.modules.teacher.bean.TeacherBean
import com.shuange.lesson.service.api.TeachersApi
import com.shuange.lesson.service.api.base.suspendExecute

class SearchTeacherViewModel : SearchBaseViewModel() {

    val teachers = ObservableArrayList<TeacherBean>()

    override val searchData: MutableList<PagerItem>
        get() {
            return teachers.toMutableList()
        }

    override fun loadData(lastIndex: String, onFinished: EmptyTask) {
        if (lastIndex == "0") {
            teachers.clear()
        }
        startBindLaunch(onFinish = onFinished) {
            val suspendResult = TeachersApi().suspendExecute()
            suspendResult.getResponse()?.body?.forEach {

                val teacher = TeacherBean().apply {
                    setTeacher(it)
                }
                teachers.add(teacher)
            }
            suspendResult.exception
        }
    }

}