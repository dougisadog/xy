package com.meten.xyh.modules.usersetting.view

import androidx.activity.viewModels
import com.meten.xyh.modules.usersetting.viewmodel.ObjectiveViewModel
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 需要提升
 */
open class ObjectiveActivity : BaseUserSettingActivity<ObjectiveViewModel>() {

    override val viewModel: ObjectiveViewModel by viewModels {
        BaseShareModelFactory()
    }

}