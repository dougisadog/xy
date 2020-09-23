package com.meten.xyh.modules.usersetting.view

import androidx.activity.viewModels
import com.meten.xyh.modules.usersetting.viewmodel.StepViewModel
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 阶段
 */
open class StepActivity : BaseUserSettingActivity<StepViewModel>() {

    override val viewModel: StepViewModel by viewModels {
        BaseShareModelFactory()
    }

}