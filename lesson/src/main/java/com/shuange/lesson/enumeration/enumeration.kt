package com.shuange.lesson.enumeration

enum class CourseState(val text: String) {
    START("尚未开始"), IN_PROGRESS("进行中"), FINISHED("已完成"), LOCKED("未解锁")
}

enum class LessonPackageType(val text: String) {
    BASE("BASE"), LIVE("LIVE"), VIDEO("VIDEO")
}

//TODO
enum class LessonType(val text: String) {
    BASE("BASE"), VIDEO("VIDEO"), AUDIO("AUDIO")
}

enum class Gender(val text: String) {
    MALE("男"), FEMALE("女"), UNKNOWN("未知")
}

enum class QuestionResourceType(val text: String) {
    ALL("ALL"), TEXT("TEXT"), VIDEO("VIDEO"), AUDIO("AUDIO")
}

enum class InputType(val text: String) {
    NONE("NONE"),
    OPTION_TEXT("OPTION_TEXT"),
    OPTION_TEXT_AUDIO("OPTION_TEXT_AUDIO"),
    OPTION_AUDIO("OPTION_AUDIO"),
    OPTION_IMAGE("OPTION_IMAGE"),
    OPTION_VIDEO("OPTION_VIDEO"),
    FILL_IN("FILL_IN"),
    SPEECH("SPEECH"),
    WRITE("WRITE")
}
