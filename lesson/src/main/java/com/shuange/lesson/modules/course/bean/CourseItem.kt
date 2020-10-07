package com.shuange.lesson.modules.course.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.shuange.lesson.enumeration.CourseState
import com.shuange.lesson.enumeration.LessonType
import com.shuange.lesson.service.response.bean.Lesson
import com.shuange.lesson.service.response.bean.Module

/**
 * lesson UI
 */
class CourseItem(private val isTitle: Boolean = false) : MultiItemEntity {

    companion object {
        const val COURSE_TITLE = 1
        const val COURSE_ITEM = 2

    }

    var courseType: String = ""

    var courseId = ""

    var name = ""

    var progress = 0

    //TODO
    var state: CourseState? = null

    var sourceUrl:String? = null

    val lessonType: LessonType?
        get() {
            try {
                return LessonType.valueOf(courseType)
            } catch (e: Exception) {
            }
            return null
        }

    fun setLesson(lesson: Lesson) {
        courseId = lesson.id.toString()
        name = lesson.name
        courseType = lesson.lessonType
        //TODO
        progress = lesson.starNum
        when(lessonType) {
            LessonType.VIDEO -> {
                sourceUrl = lesson.resourceVideoUrl
            }
        }
    }

    fun setModule(module: Module) {
        courseId = module.id.toString()
        name = module.name
        progress = module.lockedStep
        //TODO
        //module.important
    }

    override fun getItemType(): Int {
        return if (isTitle) COURSE_TITLE else COURSE_ITEM
    }
}