package com.shuange.lesson.utils.extension

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.setOnSearchListener(onSearch: (String) -> Unit) {
    setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onSearch.invoke(text.toString())
        }
        true
    }
}