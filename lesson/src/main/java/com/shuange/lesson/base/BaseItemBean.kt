package com.shuange.lesson.base

import com.shuange.lesson.service.response.bean.Wheel
import java.io.Serializable

open class BaseItemBean(
    var title: String = "",
    var content: String = "",
    var image: String = ""
):PagerItem ,Serializable {
    var id = ""
    var teacherName = ""
    var courseCount = 0
    //todo
    var time = ""
    //热度
    var heat = 0


    val teacherText:String
    get() {
        return "主讲老师：$teacherName"
    }

    val courseCountText:String
        get() {
            return "课时：$courseCount"
        }

    val timeText:String
        get() {
            return "时间：$time"
        }

    val heatText:String
        get() {
            return "热度：$heat"
        }

    override fun getItemId(): String {
        return id
    }

    fun setWheel(wheel: Wheel) {
        id = wheel.redirectToId.toString()
        title = wheel.redirectToTitle ?: ""
        content = wheel.content ?: ""
        image = wheel.imageUrl ?: ""
    }
}