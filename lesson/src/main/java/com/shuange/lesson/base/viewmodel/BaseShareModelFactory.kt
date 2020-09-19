package com.shuange.lesson.base.viewmodel

import android.app.Application
import androidx.lifecycle.*
import corelib.util.ContextManager

class BaseShareModelFactory :
    ViewModelProvider.AndroidViewModelFactory(ContextManager.getContext() as Application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.newInstance()
    }
}