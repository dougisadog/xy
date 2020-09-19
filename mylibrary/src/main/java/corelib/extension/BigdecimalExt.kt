package corelib.extension

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.roundInt():Int {
    return setScale(0, RoundingMode.HALF_EVEN).toInt()
}