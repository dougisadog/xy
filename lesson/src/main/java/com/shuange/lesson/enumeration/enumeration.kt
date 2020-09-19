package com.shuange.lesson.enumeration

enum class CourseState(val text: String) {
    START("尚未开始"), IN_PROGRESS("进行中"), FINISHED("已完成"), LOCKED("未解锁")
}

enum class LessonType(val text: String) {
    BASE("BASE"), VIDEO("VIDEO")
}

enum class Gender(val text: String) {
    MALE("男"), FEMALE("女"), UNKNOWN("未知")
}
