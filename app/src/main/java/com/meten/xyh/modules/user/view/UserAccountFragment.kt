package com.meten.xyh.modules.user.view

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.databinding.FragmentUserAccountBinding
import com.meten.xyh.modules.user.viewmodel.UserAccountViewModel
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.modules.topquality.view.CollectionActivity
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.view.NonDoubleClickListener


/**
 * 我的
 */
class UserAccountFragment : BaseFragment<FragmentUserAccountBinding, UserAccountViewModel>() {

    override val viewModel: UserAccountViewModel by viewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.fragment_user_account
    override val viewModelId: Int
        get() = BR.userAccountViewModel


    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.createUserCl.setOnClickListener((NonDoubleClickListener {
//            UserInfoActivity.start(requireContext())
            CreateUserActivity.star(requireContext())
        }))
        binding.changeAccountCl.setOnClickListener((NonDoubleClickListener {
            ChangeUserActivity.start(requireContext())
        }))
        binding.accountOrderCl.setOnClickListener((NonDoubleClickListener {
            //TODO
            ToastUtil.show("my order")
        }))
        binding.accountRemarkCl.setOnClickListener((NonDoubleClickListener {
            CollectionActivity.start(requireContext())
        }))
        binding.accountSupportCl.setOnClickListener((NonDoubleClickListener {
            callPhone()
        }))
    }

    override fun initViewObserver() {
    }


    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String = getString(R.string.support_tel)) {
        val intent = Intent(Intent.ACTION_CALL)
        val data: Uri = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivity(intent)
    }

}