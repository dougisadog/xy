package com.shuange.lesson.modules.teacher.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.modules.teacher.bean.TeacherBean
import com.shuange.lesson.modules.topquality.bean.CourseBean
import com.shuange.lesson.service.api.TeacherApi
import com.shuange.lesson.service.api.base.suspendExecute

class TeacherInfoViewModel : BaseViewModel() {

    var teacherId: String? = null

    val courses = ObservableArrayList<CourseBean>()

    val teacher = MutableLiveData<TeacherBean>()

    fun loadData() {
        teacherId?.let { id ->
            startBindLaunch {
                val suspendResult = TeacherApi(id).suspendExecute()
                suspendResult.getResponse()?.body?.let {
                    teacher.value = TeacherBean().apply {
                        setTeacher(it)
                    }
                    val source = mutableListOf<CourseBean>()
                    it.lessonPackages.forEach {
                        source.add(CourseBean().apply {
                            setLessonPackages(it)
                        })
                    }
                    courses.addAll(source)
                }
                suspendResult.exception
            }
        }
//        testData()
    }

    fun testData() {
        val baseImg =
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3844276591,3933131866&fm=26&gp=0.jpg"
        teacher.value = TeacherBean().apply {
            name = "name"
            introduction = "introduction"
            image = baseImg
            detailContent =
                "上课条理清晰，重难点明确\n\n英语专业八级，美语纯正地道，专业功底扎实，授课风趣幽默，是学生的良师益友。擅长初中英语语法、词汇、句法以及对中考中的各大题型有深入的研究，从事英语教学多年，积累了丰富的教学经验。"
        }
        for (i in 0 until 6) {
            courses.add(CourseBean().apply {
                title = "topQuality$i"
                content = "topQuality content$i"
                image = baseImg
                freeType =
                    if (i == 0) null else if (i == 1) CourseBean.FREE_TYPE_GREEN else CourseBean.FREE_TYPE_ORANGE
            })
        }
    }
}