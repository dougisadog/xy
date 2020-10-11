package com.meten.xyh.modules.usersetting.view

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivityBaseSettingBinding
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.modules.main.view.MainActivity
import com.meten.xyh.modules.usersetting.adapter.BaseUserSettingAdapter
import com.meten.xyh.modules.usersetting.viewmodel.BaseUserSettingViewModel
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.view.NonDoubleClickListener
import kotlin.reflect.KClass


/**
 * 设定
 */
abstract class BaseUserSettingActivity<T : BaseUserSettingViewModel> :
    BaseActivity<ActivityBaseSettingBinding, T>() {

    companion object {
        fun start(
            context: Context,
            type: UserSettingType,
            isSetting: Boolean = true,
            isCreate: Boolean = false
        ) {
            var targetClass: KClass<*>? = null
            when (type) {
                UserSettingType.STAGE -> {
                    targetClass = StepActivity::class
                }
                UserSettingType.OBJECTIVE -> {
                    targetClass = ObjectiveActivity::class
                }
            }
            targetClass?.let {
                val i = Intent(context, targetClass.java)
                i.putExtra(IntentKey.SETTING_KEY, isSetting)
                i.putExtra(IntentKey.CREATE_KEY, isCreate)
                context.startActivity(i)
            }
        }
    }

    var isSetting = false

    var isCreate = false

    private val baseUserSettingAdapter: BaseUserSettingAdapter by lazy {
        BaseUserSettingAdapter(
            layout = R.layout.layout_base_user_setting_item,
            data = viewModel.userSettingItems
        )
    }


    override val layoutId: Int
        get() = R.layout.activity_base_setting
    override val viewModelId: Int
        get() = BR.baseUserSettingViewModel

    override fun initParams() {
        super.initParams()
        intent?.getBooleanExtra(IntentKey.SETTING_KEY, false)?.let {
            isSetting = it
        }
        intent?.getBooleanExtra(IntentKey.CREATE_KEY, false)?.let {
            isCreate = it
        }
    }

    override fun initView() {
        viewModel.loadData()
        initSelections()
        initListener()
    }

    fun initSelections() {
        with(binding.stepRv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this@BaseUserSettingActivity,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            baseUserSettingAdapter.setOnItemClickListener { adapter, view, position ->
                baseUserSettingAdapter.data.forEachIndexed { index, stepBean ->
                    stepBean.isSelected = position == index
                }
                baseUserSettingAdapter.notifyDataSetChanged()
            }
            isNestedScrollingEnabled = false
            adapter = baseUserSettingAdapter
        }
    }

    private fun initListener() {
        binding.nextTv.setOnClickListener(NonDoubleClickListener {
            viewModel.userSettingItems.firstOrNull { it.isSelected }?.let {
                viewModel.saveSetting(it.title, DataCache.generateNewSubUser(isCreate))
            }

        })
    }


    override fun initViewObserver() {
        if (!isCreate) {
            viewModel.settingUpdated.observe(this, Observer {
                if (!isSetting) {
                    MainActivity.start(this)
                }
                finish()
            })
        }
    }


}