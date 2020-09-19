package com.shuange.lesson.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.shuange.lesson.base.viewmodel.BaseViewModel
import java.lang.ref.WeakReference

abstract class BaseFragment<BD : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    lateinit var binding: BD

    abstract val viewModel: VM

    abstract val layoutId: Int

    open val viewModelId: Int = 0

    var isDisplay = true

    var remoteFragment: WeakReference<BaseFragment<*, *>>? = null

    open var fragmentContainerId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, null, true)
        if (viewModelId != 0) {
            binding.setVariable(viewModelId, viewModel)
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initParams()
        initView()
        lifecycleScope.launchWhenResumed {
            initObserver()
            initViewObserver()
        }
    }

    abstract fun initView()

    abstract fun initViewObserver()

    open fun onBackPressed(): Boolean {
        return true
    }

    open fun initParams() {

    }

    private fun initObserver() {
        viewModel.showLoading.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                baseActivity()?.showLoading(it)
            }
        })
    }

    private fun baseActivity(): BaseActivity<*, *>? {
        return (requireActivity() as? BaseActivity<*, *>)
    }

    fun showFragmentByActivity(fragment: BaseFragment<*, *>) {
        fragment.remoteFragment = WeakReference(this)
        isDisplay = false
        baseActivity()?.showFragment(fragment)
    }

    fun showFragment(fragment: BaseFragment<*, *>) {
        val containerId = fragmentContainerId ?: return
        try {
            fragment.remoteFragment = WeakReference(this)
            isDisplay = false
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(containerId, fragment).commitNowAllowingStateLoss()
        } catch (e: Exception) {
        }
    }

    fun finish() {
        try {
            remoteFragment?.get()?.let {
                it.isDisplay = true
            }
            parentFragmentManager.beginTransaction().remove(this).commitNowAllowingStateLoss()
        } catch (e: Exception) {
        }
    }
}