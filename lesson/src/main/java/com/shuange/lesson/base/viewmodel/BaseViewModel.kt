package com.shuange.lesson.base.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shuange.lesson.EmptyTask
import corelib.util.ContextManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.lang.Exception

open class BaseViewModel : AndroidViewModel(ContextManager.getContext()) {

    val showLoading = MutableLiveData<Boolean>()

    fun startBindLaunch(
        showLoading: Boolean = true,
        onFinish: EmptyTask = null,
        block: suspend CoroutineScope.() -> Exception?
    ) {
        val viewModel = this
        if (showLoading) {
            viewModel.showLoading.value = true
        }
        viewModelScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                supervisorScope {
                    var error: Throwable? = null
                    val result = kotlin.runCatching {
                        error = block.invoke(this)
                    }
                    error = error ?: result.exceptionOrNull()
                    when (error) {
                        //TODO exception
                    }

                }
            }
            if (showLoading) {
                viewModel.showLoading.value = true
            }
            onFinish?.invoke()
        }
    }
}
