package com.shuange.lesson.modules.course.bean

import com.shuange.lesson.enumeration.CourseState
import com.shuange.lesson.enumeration.LessonType
import com.shuange.lesson.service.response.bean.Lesson

/**
 * lesson UI
 */
open class CourseLessonItem : StarOwner() {

    var courseType: String = ""

    var lessonId: Long = 0

    override val itemId: Long
        get() = lessonId

    //TODO
    var state: CourseState? = null

    var sourceUrl: String? = null

    val lessonType: LessonType?
        get() {
            try {
                return LessonType.valueOf(courseType)
            } catch (e: Exception) {
            }
            return null
        }

    fun setLesson(lesson: Lesson) {
        lessonId = lesson.id
        name = lesson.name
        courseType = lesson.lessonMode
        //TODO
        stars = lesson.starNum
        when (lessonType) {
            LessonType.VIDEO -> {
                sourceUrl = lesson.resourceVideoUrl
            }
        }
    }


}