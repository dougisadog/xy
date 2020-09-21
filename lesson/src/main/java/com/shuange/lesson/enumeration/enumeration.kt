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

enum class QuestionResourceType(val text: String) {
    ALL("ALL"), TEXT("TEXT"), VIDEO("VIDEO"), AUDIO("AUDIO")
}

enum class InputType(val text: String) {
    OPTION_TEXT("OPTION_TEXT"),
    OPTION_TEXT_AUDIO("OPTION_TEXT_AUDIO"),
    OPTION_AUDIO("OPTION_AUDIO"),
    OPTION_IMAGE("OPTION_IMAGE"),
    FILL_IN("FILL_IN"),
    SPEECH("SPEECH")
}
