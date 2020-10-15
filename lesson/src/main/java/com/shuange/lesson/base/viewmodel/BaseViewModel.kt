package com.shuange.lesson.base.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shuange.lesson.EmptyTask
import com.shuange.lesson.base.config.AppConfig
import com.shuange.lesson.utils.ToastUtil
import corelib.util.ContextManager
import corelib.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

open class BaseViewModel : AndroidViewModel(ContextManager.getContext()) {

    val error = MutableLiveData<String>()

    val showLoading = MutableLiveData<Boolean>()

    fun startBindLaunch(
        showLoading: Boolean = false,
        onFinish: EmptyTask = null,
        block: suspend CoroutineScope.() -> Exception?
    ) {
        val viewModel = this
        if (showLoading) {
            viewModel.showLoading.value = true
        }
        viewModelScope.launch(Dispatchers.Main) {
            if (AppConfig.DEBUG) {
                supervisorScope {
                    handleError(this, block)
                }
            } else {
                kotlin.runCatching {
                    supervisorScope {
                        handleError(this, block)
                    }
                }
            }
            if (showLoading) {
                viewModel.showLoading.value = true
            }
            onFinish?.invoke()
        }
    }

    private suspend fun handleError(scope: CoroutineScope, block: suspend CoroutineScope.() -> Exception?) {
        var error: Throwable? = null
        val result = kotlin.runCatching {
            error = block.invoke(scope)
        }
        error = error ?: result.exceptionOrNull()
        error?.let {
            var message = it.message ?: ""
            Log.e("error", message)
            if (message.isBlank()) {
                message = "未知错误"
            }
            ToastUtil.show(message)
        }
        when (error) {

        }
    }
}
