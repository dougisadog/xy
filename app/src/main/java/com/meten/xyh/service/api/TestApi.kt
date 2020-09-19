package com.meten.xyh.service.api

import com.meten.xyh.modules.main.bean.MainBean
import com.shuange.lesson.service.api.base.BaseApi
import kotlin.reflect.KClass

class TestApi : BaseApi<MainBean>() {

    override val resultClass: KClass<MainBean>
        get() = MainBean::class
}