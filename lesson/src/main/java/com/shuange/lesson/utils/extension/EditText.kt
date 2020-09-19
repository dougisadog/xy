package com.shuange.lesson.utils.extension

import android.view.KeyEvent
import android.widget.EditText

fun EditText.setOnSearchListener(onSearch: (String) -> Unit) {
    setOnEditorActionListener { v, actionId, event ->
        if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
            onSearch.invoke(text.toString())
        }
        true
    }
}