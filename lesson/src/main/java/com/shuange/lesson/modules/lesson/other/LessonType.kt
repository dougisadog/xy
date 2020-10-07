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

        fun getLessonType(
            inputType: InputType?
        ): LessonType? {
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

