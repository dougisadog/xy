package com.meten.xyh.modules.usersetting.view

import androidx.activity.viewModels
import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.usersetting.viewmodel.StepViewModel
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 阶段
 */
open class StepActivity : BaseUserSettingActivity<StepViewModel>() {

    override val viewModel: StepViewModel by viewModels {
        BaseShareModelFactory()
    }

    override fun initParams() {
        super.initParams()
        viewModel.default = DataCache.generateNewSubUser(isCreate)?.stage
    }

    override fun initView() {
        super.initView()
        binding.title.text = "请选择您目前的学习阶段"
    }
}