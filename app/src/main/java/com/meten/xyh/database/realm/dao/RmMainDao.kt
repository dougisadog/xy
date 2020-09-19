package com.meten.xyh.database.realm.dao

import com.meten.xyh.database.realm.bean.RmMainModel
import com.shuange.lesson.realm.dao.BaseDao
import io.realm.Realm

class RmMainDao : BaseDao<RmMainModel>() {

    fun <R> getAll(converter: (RmMainModel) -> R): MutableList<R> {
        val result = mutableListOf<R>()
        val mRealm = Realm.getDefaultInstance()
        mRealm.use { r ->
            val data = r.where(RmMainModel::class.java).findAll().map {
                converter(it)
            }
            result.addAll(data)
            return result
        }
    }

}