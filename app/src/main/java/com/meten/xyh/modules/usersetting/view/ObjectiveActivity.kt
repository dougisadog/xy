package com.meten.xyh.modules.usersetting.view

import androidx.activity.viewModels
import com.meten.xyh.base.DataCache
import com.meten.xyh.modules.usersetting.viewmodel.ObjectiveViewModel
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory


/**
 * 需要提升
 */
open class ObjectiveActivity : BaseUserSettingActivity<ObjectiveViewModel>() {

    override val viewModel: ObjectiveViewModel by viewModels {
        BaseShareModelFactory()
    }

    override fun initParams() {
        super.initParams()
        viewModel.default = DataCache.generateNewSubUser(isCreate)?.objective
    }

    override fun initView() {
        super.initView()
        binding.title.text = "请选择您需要提升"

    }

}