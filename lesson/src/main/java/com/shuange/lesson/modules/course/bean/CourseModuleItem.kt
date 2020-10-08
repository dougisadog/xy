package com.shuange.lesson.modules.course.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.shuange.lesson.service.response.bean.Module
import com.shuange.lesson.service.response.bean.ModuleRecord

/**
 * module UI
 */
class CourseModuleItem(val isTitle: Boolean = false) : StarOwner(), MultiItemEntity {

    companion object {
        const val COURSE_TITLE = 1
        const val COURSE_ITEM = 2
    }

    var moduleId: Long = 0
    var record: ModuleRecord? = null

    fun setModule(module: Module) {
        moduleId = module.id
        name = module.name
        stars = module.starNum
        record = module.record
    }

    override val itemId: Long
        get() = moduleId

    override fun getItemType(): Int {
        return if (isTitle) COURSE_TITLE else COURSE_ITEM
    }
}