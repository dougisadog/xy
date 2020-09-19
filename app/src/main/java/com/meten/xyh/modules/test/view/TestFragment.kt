package com.meten.xyh.modules.test.view

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.meten.xyh.BR
import com.meten.xyh.R
import com.meten.xyh.base.config.IntentKey
import com.meten.xyh.databinding.FragmentTestBinding
import com.meten.xyh.modules.main.viewmodel.ShareViewModel
import com.meten.xyh.modules.test.viewmodel.TestViewModel
import com.shuange.lesson.base.BaseFragment
import com.shuange.lesson.base.viewmodel.BaseShareModelFactory
import com.shuange.lesson.utils.Cancelable
import com.shuange.lesson.utils.ToastUtil
import com.shuange.lesson.utils.extension.onClick
import com.shuange.lesson.view.NonDoubleClickListener

@Cancelable
class TestFragment : BaseFragment<FragmentTestBinding, TestViewModel>() {

    companion object {
        fun newInstance(param: String, color: String = ""): TestFragment {
            val f = TestFragment()
            Bundle().apply {
                putString(IntentKey.TEST_P, param)
                putString(IntentKey.TEST_COLOR, color)
                f.arguments = this
            }
            return f
        }
    }

    override val viewModel: TestViewModel by viewModels {
        BaseShareModelFactory()
    }

    private val shareViewModelModel: ShareViewModel by activityViewModels {
        BaseShareModelFactory()
    }

    override val layoutId: Int
        get() = R.layout.fragment_test
    override val viewModelId: Int
        get() = BR.testViewModel

    override var fragmentContainerId: Int? = R.id.testFragmentContainerFl

    override fun initParams() {
        super.initParams()
        arguments?.let {
            it.getString(IntentKey.TEST_P)?.let { s ->
                ToastUtil.show(s)
            }
            it.getString(IntentKey.TEST_COLOR)?.let { s ->
                viewModel.color.value = s
            }
        }
    }

    override fun initView() {
        binding.setVariable(BR.shareViewModel, shareViewModelModel)
        viewModel.loadData()
        initListener()
    }

    private fun initListener() {
        binding.titleTv.setOnClickListener(NonDoubleClickListener {
            shareViewModelModel.shareValue.value = Math.random().toString() + "share"
        })
        binding.backIv.setOnClickListener(NonDoubleClickListener {
            finish()
        })
        binding.fragmentTv.onClick {
            showFragment(TestFragment.newInstance("from ${this::class.java.simpleName}"))
        }
    }

    override fun initViewObserver() {
    }
}