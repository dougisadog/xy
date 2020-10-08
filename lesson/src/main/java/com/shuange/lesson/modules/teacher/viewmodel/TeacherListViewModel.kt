package com.shuange.lesson.modules.teacher.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.teacher.bean.TeacherBean
import com.shuange.lesson.service.api.TeachersApi
import com.shuange.lesson.service.api.base.suspendExecute

class TeacherListViewModel : BaseViewModel() {

    val teachers = ObservableArrayList<TeacherBean>()

    var searchText = MutableLiveData<String>()

    fun loadData() {
        loadTeachers("0")
    }

    fun loadTeachers(
        startId: String = teachers.lastOrNull()?.id ?: "0",
        page: Int = 50,
        onFinished: EmptyTask = null
    ) {
        startBindLaunch(onFinish = onFinished) {
            val suspendResult = TeachersApi().apply { addPageParam(startId) } .suspendExecute()
            suspendResult.getResponse()?.body?.forEach {
                teachers.add(TeacherBean().apply {
                    setTeacher(it)
                })
            }
            suspendResult.exception
        }
//        testData()
    }

    fun testData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        for (i in 0 until 6) {
            teachers.add(TeacherBean().apply {
                name = "name$i"
                introduction = "introduction$i"
                image = baseImg
                subTitle = "郭老师·美式发音速成课$i"
            })
        }
    }
}