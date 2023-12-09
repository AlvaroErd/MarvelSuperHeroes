package com.alerdoci.marvelsuperheroes.app.common.validation

import android.text.TextUtils
import android.widget.EditText

class EditTextEqualsCompareValidator(editText: EditText, private val text: String, errorMessageId: Int) : EditTextValidator(editText, errorMessageId) {

    override val isValid: Boolean
        get() = if (EditTextUtils.testIsEmpty(editText) || TextUtils.isEmpty(text)) {
            false
        } else {
            editText.text.toString() != text
        }
}
