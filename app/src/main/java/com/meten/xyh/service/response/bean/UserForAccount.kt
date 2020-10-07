package com.meten.xyh.service.response.bean

import com.shuange.lesson.service.response.bean.BaseBean

class UserForAccount : BaseBean() {
    var answer: String = ""
    var lessonId: Long = 0
    var lessonMode: String = ""
    var lessonModuleId: Long = 0
    var lessonModuleName: String = ""
    var lessonName: String = ""
    var lessonPackageId: Long = 0
    var lessonPackageName: String = ""
    var lessonType: String = ""
    var questionId: Long = 0
    var score: Int = 0
    var subUserId: Long = 0
    var subUserName: String = ""
    var userId: Long = 0
}
