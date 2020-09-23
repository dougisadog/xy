package com.meten.xyh.modules.usersetting.view

import androidx.activity.viewModels
import com.meten.xyh.modules.usersetting.viewmodel.InterestViewModel
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 兴趣
 */
open class InterestActivity : BaseUserSettingActivity<InterestViewModel>() {

    override val viewModel: InterestViewModel by viewModels {
        BaseShareModelFactory()
    }

}