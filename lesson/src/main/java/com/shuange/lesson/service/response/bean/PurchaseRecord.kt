package com.shuange.lesson.service.response.bean

import com.shuange.lesson.enumeration.PurchaseType

class PurchaseRecord : BaseBean() {
    var amount: Int = 0
    var login: String? = null
    var phone: String? = null
    var purchaseContent: String? = null
    var purchaseId: Int = 0
    var type: String? = null
    var userId: Long = 0

    fun getType(): PurchaseType? {
        try {
            return PurchaseType.valueOf(type ?: return null)
        } catch (e: Exception) {
        }
        return null
    }
}



