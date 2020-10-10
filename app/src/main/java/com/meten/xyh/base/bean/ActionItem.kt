package com.meten.xyh.base.bean

import com.meten.xyh.enumeration.UserSettingType
import com.shuange.lesson.EmptyTask

class ActionItem(type:UserSettingType) {
    var title = ""
    var value = ""
    var action: EmptyTask = null
}