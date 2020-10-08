package com.shuange.lesson.service.api.base.exception

/**
 * 400 逻辑错误返回
 */
data class LogicErrorResponse(
    val message: String?,
    val status: Int,
    val title: String?,
    val type: String?
)