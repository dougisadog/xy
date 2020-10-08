package com.meten.xyh.modules.usersetting.view

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.DataCache
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.ActivitySignatureBinding
import com.meten.xyh.enumeration.UserSettingType
import com.meten.xyh.modules.usersetting.viewmodel.SignatureViewModel
import com.meten.xyh.service.response.bean.SubUser
import com.shuange.lesson.base.BaseActivity
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import kotlinx.android.synthetic.main.layout_header.view.*


/**
 * 个性签名/昵称
 */
class SignatureActivity(val subUser: SubUser? = DataCache.newSubUser) : BaseActivity<ActivitySignatureBinding, SignatureViewModel>() {

    companion object {
        fun start(context: Context, type: UserSettingType) {
            val i = Intent(context, SignatureActivity::class.java)
            i.putExtra(IntentKey.SIGNATURE_TYPE_KEY, type.ordinal)
            context.startActivity(i)
        }
    }

    override val viewModel: SignatureViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.activity_signature
    override val viewModelId: Int
        get() = BR.changeUserViewModel

    override fun initParams() {
        super.initParams()
        intent.getIntExtra(IntentKey.SIGNATURE_TYPE_KEY, 0).let {
            viewModel.settingChange = SignatureViewModel.SettingChange(subUser).apply {
                setUserSettingType(UserSettingType.values()[it])
            }
        }
    }

    override fun initView() {
        binding.header.title.text = viewModel.settingChange?.title
        binding.header.rightBtn.text = "保存"
        binding.header.rightBtn.visibility = View.VISIBLE
        initListener()
    }


    private fun initListener() {
        binding.header.back.setOnClickListener {
            finish()
        }
        binding.header.rightBtn.setOnClickListener {
            viewModel.saveSetting()
        }
        binding.clearIv.rightBtn.setOnClickListener {
            viewModel.signature.value = ""
        }

    }


    override fun initViewObserver() {
    }

}