package com.meten.xyh.database.realm.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RmMainModel: RealmObject() {

    @PrimaryKey
    var name = ""

    var content = ""
}