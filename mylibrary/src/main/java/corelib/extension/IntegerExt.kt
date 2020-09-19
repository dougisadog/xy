package corelib.extension

import java.lang.Math.abs

val Int?.count: Int
    get() {
        if (this == null) {
            return 0
        }
        return (abs(this)).toString().length
    }
val Int?.stringValue: String
    get() {
        if (this == null) {
            return ""
        }
        return this.toString()
    }

val Long?.stringValue: String
    get() {
        if (this == null) {
            return ""
        }
        return this.toString()
    }