package corelib.extension

val Boolean?.check: Boolean
    get() {
        return this ?: false
    }