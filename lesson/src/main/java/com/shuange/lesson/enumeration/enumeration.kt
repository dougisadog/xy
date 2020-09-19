package com.shuange.lesson.enumeration

enum class CourseState(val text:String) {
    START("尚未开始"), IN_PROGRESS("进行中"), FINISHED("已完成"), LOCKED("未解锁")
}