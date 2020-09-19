package corelib.extension

import corelib.CGFloat

val Double.Companion.leastNormalMagnitude: CGFloat get() = 0.0

val Double.Companion.nan: CGFloat
    get() = Double.NaN

val Double.isNaN: Boolean
    get() = isNaN()