package com.shuange.lesson.view

import android.view.View

class NonDoubleClickListener(var click: () -> Unit) : View.OnClickListener {

    companion object {
        const val minDelay = 300L
    }

    var lastTime = 0L

    override fun onClick(p0: View?) {
        val oldTime = lastTime
        lastTime = System.currentTimeMillis()
        if (oldTime == 0L && lastTime - oldTime > minDelay) {
            click.invoke()
            lastTime = 0L
        }
    }
}