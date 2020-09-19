package com.meten.xyh.database.realm.datasource

import com.meten.xyh.database.realm.bean.RmMainModel
import com.meten.xyh.database.realm.dao.RmMainDao
import com.meten.xyh.modules.main.bean.MainBean

class RmMainDataSource {

    val dao: RmMainDao by lazy {
        RmMainDao()
    }

    fun getMainBean(): MutableList<MainBean> {
        val result = dao.getAll(converter = {
            MainBean(
                title = it.name,
                content = it.content
            )
        })
        return result
    }

    fun save(bean: MainBean) {
        val model = RmMainModel().apply {
            name = bean.title
            content = bean.content
        }
        dao.save(model)
    }
}