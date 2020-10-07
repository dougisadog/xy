package com.shuange.lesson.modules.topquality.bean

import com.shuange.lesson.base.BaseItemBean
import com.shuange.lesson.base.PagerItem
import com.shuange.lesson.enumeration.LessonPackageType
import com.shuange.lesson.service.response.bean.LessonPackage
import com.shuange.lesson.service.response.bean.Record
import corelib.util.Log
import java.io.Serializable

/**
 * lesson package UI
 */
open class CourseBean : BaseItemBean(), PagerItem, Serializable {

    var courseId = ""

    var price = 0

    var packageType: LessonPackageType? = null

    var record:Record?= null

    fun setLessonPackages(lessonPackage: LessonPackage) {
//        image = lessonPackage
        courseId = lessonPackage.id.toString()
        title = lessonPackage.name
        content = lessonPackage.description
        price = lessonPackage.price
        //TODO 已购/免费 类型解析
        if (lessonPackage.haveBought) {
            freeType = FREE_TYPE_ORANGE
        } else {
            freeType = null
        }
        try {
            // lessonPackage.lessonType 暂时无用
            packageType = LessonPackageType.valueOf(lessonPackage.lessonMode)
        } catch (e: Exception) {
            Log.e("courseBean", "lesson package type 转换错误 ${e.message}")
        }
        record = lessonPackage.record
    }

    //0 绿 1 黄 限时免费 2直播付费 3已购
    var freeType: Int? = FREE_TYPE_GREEN

    companion object {
        const val FREE_TYPE_GREEN = 0
        const val FREE_TYPE_ORANGE = 1

        const val PAY_TYPE_STEAM = 2
        const val PAID_TYPE = 3

    }

    override fun getItemId(): String {
        return id
    }
}