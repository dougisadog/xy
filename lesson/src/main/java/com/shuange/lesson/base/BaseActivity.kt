package com.shuange.lesson.base

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.shuange.lesson.base.viewmodel.BaseViewModel
import com.shuange.lesson.utils.AnnotationParser
import com.shuange.lesson.utils.ToastUtil

abstract class BaseActivity<BD : ViewDataBinding, VM : BaseViewModel> : FragmentActivity() {

    lateinit var binding: BD

    abstract val viewModel: VM

    abstract val layoutId: Int

    open val viewModelId: Int = 0

    open var fragmentContainerId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        if (viewModelId != 0) {
            binding.setVariable(viewModelId, viewModel)
        }
        binding.lifecycleOwner = this
        initParams()
        initView()
        initObserver()
        initViewObserver()
    }

    abstract fun initView()

    abstract fun initViewObserver()

    open fun initParams() {

    }

    private fun initObserver() {
        viewModel.error.observe(this, Observer {
            if (!it.isNullOrBlank()) {
                ToastUtil.show(it)
            }
        })
        viewModel.showLoading.observe(this, Observer {
            if (null != it) {
                showLoading(it)
            }
        })
    }

    fun showLoading(isShow: Boolean) {

    }

    fun showFragment(fragment: BaseFragment<*, *>) {
        val containerId = fragmentContainerId ?: return
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(containerId, fragment).commitNowAllowingStateLoss()
        } catch (e: Exception) {
            Log.e("showFragment", e.message ?: "")
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (popBackStack()) {
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun popBackStack(): Boolean {
        val fragments = supportFragmentManager.fragments
        fragments.reverse()
        fragments.forEach {
            val currentFragment = AnnotationParser.nextFragment(it)
            if (null != currentFragment) {
                if (currentFragment is BaseFragment<*, *>) {
                    currentFragment.finish()
                } else {
                    supportFragmentManager.beginTransaction().remove(currentFragment)
                        .commitNowAllowingStateLoss()
                }
                return true
            }
        }
        return false
    }
}