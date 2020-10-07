package com.shuange.lesson.modules.lesson.other

import com.shuange.lesson.enumeration.InputType

enum class LessonType {
    TYPE_01,
    TYPE_02,
    TYPE_03,
    TYPE_05,
    TYPE_06,
    TYPE_07,
    TYPE_08,
    TYPE_10,
    TYPE_13,
    TYPE_14,
    TYPE_15,
    TYPE_16;

    companion object {
        //        enum class QuestionResourceType(val text: String) {
//            ALL("ALL"), TEXT("TEXT"), VIDEO("VIDEO"), AUDIO("AUDIO")
//        }
//
//        enum class InputType(val text: String) {
//            OPTION_TEXT("OPTION_TEXT"),
//            OPTION_TEXT_AUDIO("OPTION_TEXT_AUDIO"),
//            OPTION_AUDIO("OPTION_AUDIO"),
//            OPTION_IMAGE("OPTION_IMAGE"),
//            FILL_IN("FILL_IN"),
//            SPEECH("SPEECH")
//        }
//        LessonType.TYPE_01 -> {
//            f = NormalLessonFragment()
//        }
//        LessonType.TYPE_02 -> {
//            f = ReadingLessonFragment()
//        }
//        LessonType.TYPE_03, LessonType.TYPE_07 -> {
//            f = SelectorLessonFragment()
//        }
//        LessonType.TYPE_05, LessonType.TYPE_06, LessonType.TYPE_08, LessonType.TYPE_13, LessonType.TYPE_14 -> {
//            f = SelectorPicLessonFragment()
//        }
//        LessonType.TYPE_10 -> {
//            f = InputLessonFragment()
//        }
//        LessonType.TYPE_15 -> {
//            f = VideoLessonFragment()
//        }
//        LessonType.TYPE_16 -> {
//            f = RecordingLessonFragment()
//        }

        fun getLessonType(
//            questionResourceType: QuestionResourceType?,
            inputType: InputType?
        ): LessonType? {
//            questionResourceType?:return null
            inputType ?: return null
            val selectorTypes = arrayListOf(
                InputType.OPTION_TEXT,
                InputType.OPTION_AUDIO,
                InputType.OPTION_TEXT_AUDIO
            )
            val normalTypes = arrayListOf(InputType.NONE)
            val selectorImageTypes = arrayListOf(InputType.OPTION_IMAGE)
            val inputTypes = arrayListOf(InputType.FILL_IN)
            val recordingTypes = arrayListOf(InputType.SPEECH)

            val writeTypes = arrayListOf(InputType.WRITE)


            if (normalTypes.contains(inputType)) {
                return TYPE_01
            } else if (recordingTypes.contains(inputType)) {
                return TYPE_02
            } else if (selectorTypes.contains(inputType)) {
                return TYPE_03
            } else if (selectorImageTypes.contains(inputType)) {
                return TYPE_05
            } else if (inputTypes.contains(inputType)) {
                return TYPE_07
            } else if (writeTypes.contains(inputType)) {
                return TYPE_10
            }
//            else if (writeTypes.contains(inputType)) {
//                return TYPE_16
//            }
            return null
        }
    }
}

