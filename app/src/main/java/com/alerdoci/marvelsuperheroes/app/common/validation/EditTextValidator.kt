package com.alerdoci.marvelsuperheroes.app.common.validation

import android.widget.EditText

abstract class EditTextValidator(val editText: EditText, private val errorMessageId: Int) {

    private var errorMessage: String? = null //error message for this instance.

    abstract val isValid: Boolean

    fun buildErrorMessage(): String {
        return editText.context?.let { context ->
            errorMessage?.let {
                errorMessage
            } ?: context.getString(errorMessageId)
        } ?: ""
    }

    fun setErrorMessage(errorMessage: String): EditTextValidator {
        this.errorMessage = errorMessage
        return this
    }
}
