package com.meten.xyh.repository.main

import com.meten.xyh.database.realm.datasource.RmMainDataSource
import com.meten.xyh.modules.main.bean.MainBean
import com.meten.xyh.modules.main.model.MainModel
import com.shuange.lesson.service.response.base.SuspendResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MainRepository {

    val mainSource by lazy {
        RmMainDataSource()
    }

    suspend fun getMainData() = withContext(Dispatchers.IO) {
        val result = MainModel()
        var error: java.lang.Exception? = null
        val bean = mainSource.getMainBean().firstOrNull()
        if (null == bean) {
            result.mainBean = MainBean("test title", "test content")
        } else {
            result.mainBean = bean
        }
        SuspendResponse(result)
            .apply { exception = error }
    }

    fun save(bean: MainBean) {
        mainSource.save(bean)
    }
}