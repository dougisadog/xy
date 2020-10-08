package com.meten.xyh.modules.usersetting.view

import android.content.Context
import android.content.Intent
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivityStepBinding
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.modules.main.view.MainActivity
import com.meten.xyh.modules.usersetting.adapter.BaseUserSettingAdapter
import com.meten.xyh.modules.usersetting.viewmodel.BaseUserSettingViewModel
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.view.NonDoubleClickListener
import kotlin.reflect.KClass


/**
 * 设定
 */
abstract class BaseUserSettingActivity<T : BaseUserSettingViewModel>(val user:SubUser? = DataCache.newSubUser) :
    BaseActivity<ActivityStepBinding, T>() {

    companion object {
        fun start(context: Context, type: UserSettingType, isSetting: Boolean = true) {
            var targetClass: KClass<*>? = null
            when (type) {
                UserSettingType.STAGE -> {
                    targetClass = StepActivity::class
                }
            }
            targetClass?.let {
                val i = Intent(context, targetClass.java)
                i.putExtra(IntentKey.SETTING_KEY, isSetting)
                context.startActivity(i)
            }
        }
    }

    var isSetting = false

    private val baseUserSettingAdapter: BaseUserSettingAdapter by lazy {
        BaseUserSettingAdapter(
            layout = R.layout.layout_base_user_setting_item,
            data = viewModel.userSettingItems
        )
    }


    override val layoutId: Int
        get() = R.layout.activity_step
    override val viewModelId: Int
        get() = BR.stepViewModel

    override fun initParams() {
        super.initParams()
        intent?.getBooleanExtra(IntentKey.SETTING_KEY, false)?.let {
            isSetting = it
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
            if (isSetting) {
                viewModel.userSettingItems.firstOrNull { it.isSelected }?.let {
                    viewModel.saveSetting(it.title, user)
                    finish()
                }

            } else {
                MainActivity.start(this)
                finish()
            }
        })
    }


    override fun initViewObserver() {
    }


}