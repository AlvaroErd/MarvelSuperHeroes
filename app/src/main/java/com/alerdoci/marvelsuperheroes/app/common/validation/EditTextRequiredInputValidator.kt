package com.alerdoci.marvelsuperheroes.app.common.validation

import android.widget.EditText

/**
 * Created by Daniel on 9/11/2015.
 */
class EditTextRequiredInputValidator(editText: EditText, errorMessageId: Int) : EditTextValidator(editText, errorMessageId) {

    override val isValid: Boolean
        get() = !EditTextUtils.testIsEmpty(editText)
}
