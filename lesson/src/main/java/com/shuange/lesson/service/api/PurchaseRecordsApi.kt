package com.shuange.lesson.service.api

import com.shuange.lesson.service.api.base.BaseTokenApi
import com.shuange.lesson.service.response.PurchaseRecordsResponse
import corelib.http.PostType
import kotlin.reflect.KClass

/**
 * 课程购买
 */
class PurchaseRecordsApi(val lessonPackageId: String) : BaseTokenApi<PurchaseRecordsResponse>() {
    init {
        post(PostType.JSON)
    }

    override val path: String
        get() = "/api/v1.0/purchase-records"
    override val resultClass: KClass<PurchaseRecordsResponse>
        get() = PurchaseRecordsResponse::class

    override fun prepareRequest() {
        super.prepareRequest()
        addQuery("lessonPackageId", lessonPackageId)
    }
}
