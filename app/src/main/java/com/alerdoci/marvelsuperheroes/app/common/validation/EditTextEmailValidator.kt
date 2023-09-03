package com.alerdoci.marvelsuperheroes.app.common.validation

import android.widget.EditText

class EditTextEmailValidator(editText: EditText, errorMessageId: Int) : EditTextValidator(editText, errorMessageId) {

    override val isValid: Boolean
        get() = if (EditTextUtils.testIsEmpty(editText)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(EditTextUtils.getText(editText)).matches()
        }
}
