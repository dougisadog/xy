package corelib

import android.os.Build
import corelib.util.OSUtils

/**
 * アプリ共通定数
 * Created by Yamamoto Keita on 2018/01/18.
 */
object AppConstants {
    val chanelCode = "L" // "J":iPhone株、"L":Android株
    val yen = "円"
    val stock = "株"
    val stockUnit = "株単位"
    val value = "値"
    val year = "年"
    val billion = "億"
    var percent = "%"
    val symbolFrom = "～"
    val maxTableRecord = 999
    val maxPrice = "9999999999"
    val maxQuantity = "99999999"
    val maxQuantityInt = AppConstants.maxQuantity.toInt()
    val readNewsCountPerPage = 50 // Fixed data number per page of each list in News screen
    val maxReadNewsRecord = 1000 // Max number of record to store read new model data

    val userAgent: String
        get() = "SBIStock2Android/${OSUtils.appVersion()}(${Build.MODEL}/${Build.VERSION.RELEASE})"
}