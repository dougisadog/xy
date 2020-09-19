package com.shuange.lesson.realm.dao

import io.realm.Realm
import io.realm.RealmObject

abstract class BaseDao<T:RealmObject> {

    fun save(bean: T) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransactionAsync {
                it.insertOrUpdate(bean)
            }
        }
    }

}