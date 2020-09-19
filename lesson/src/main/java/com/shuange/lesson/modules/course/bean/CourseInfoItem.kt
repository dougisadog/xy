package com.shuange.lesson.modules.course.bean

import com.shuange.lesson.service.response.bean.LessonPackage

class CourseInfoItem {

    var image: String = ""

    var courseId = ""

    var title = ""

    var introduction = ""

    fun setLessonPackages(lessonPackage: LessonPackage) {
//        image = lessonPackage
        courseId = lessonPackage.id.toString()
        title = lessonPackage.name
        introduction = lessonPackage.description
    }

}