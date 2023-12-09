package com.alerdoci.marvelsuperheroes.app.common.validation

import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

object EditTextUtils {

    const val NAME_REGULAR_EXPRESSION = "^[\\u00C0-\\u1FFF\\u2C00-\\uD7FF\\w|\\s|\\'|-]+$" // See: https://stackoverflow.com/a/150078/5189200
    const val PASSWORD_REGULAR_EXPRESSION = "^[\\w]{6,}$" // Firebase asks for 6 characters, at least
    const val NO_ERROR_MESSAGE = ""

    fun clearError(editText: EditText) {
        if (editText.parent != null && editText.parent is TextInputLayout) {
            //((TextInputLayout) editText.getParent()).setErrorEnabled(false);
            (editText.parent as TextInputLayout).error = NO_ERROR_MESSAGE
        } else if (editText.parent != null
            && editText.parent.parent != null && editText.parent.parent is TextInputLayout
        ) {
            //((TextInputLayout) editText.getParent().getParent()).setErrorEnabled(false);
            (editText.parent.parent as TextInputLayout).error = NO_ERROR_MESSAGE
        } else {
            editText.error = null
        }
    }

    fun getAmount(editText: EditText?): Double {
        return if (editText == null) {
            0.0
        } else {
            CurrencyUtils.parseAmount(getText(editText))
        }
    }

    fun getText(editText: EditText?): String {
        return editText?.text?.toString() ?: ""
    }

    fun handleViewPassword(passwordField: EditText?, event: MotionEvent?) {
        if (passwordField == null || event == null) {
            return
        }

        val selectedPosition = passwordField.selectionEnd

        if (event.action == MotionEvent.ACTION_DOWN) {
            passwordField.transformationMethod = null
        } else if (event.action == MotionEvent.ACTION_UP) {
            passwordField.transformationMethod = PasswordTransformationMethod()
        }

        passwordField.setSelection(selectedPosition, selectedPosition)
    }

    fun isEmpty(editText: EditText, message: String): Boolean {
        val isEmpty = testIsEmpty(editText)

        if (isEmpty) {
            if (editText.visibility != View.VISIBLE) {
                return false
            }
            setError(editText, message)
        } else {
            clearError(editText)
        }
        return isEmpty
    }

    fun isEmpty(message: String, vararg editTexts: EditText): Boolean {
        var isEmpty = false
        for (editText in editTexts) {
            if (isEmpty(editText, message)) {
                isEmpty = true
            }
        }

        return isEmpty
    }

    fun isValid(editTextValidator: EditTextValidator): Boolean {
        return isValid(editTextValidator, true)
    }

    private fun isValid(editTextValidator: EditTextValidator, clearError: Boolean): Boolean {
        val isValid = editTextValidator.isValid
        if (!isValid) {
            val errorMessage = editTextValidator.buildErrorMessage()
            Log.d("isValid", errorMessage)
            setError(editTextValidator.editText, errorMessage)
        } else if (clearError) {
            clearError(editTextValidator.editText)
        }
        return isValid
    }

    fun isValid(vararg editTextValidators: EditTextValidator): Boolean {
        var isValid = true
        for (editTextValidator in editTextValidators) {
            isValid = isValid && isValid(editTextValidator, isValid)
        }
        return isValid
    }

    fun reset(editText: EditText?) {
        editText?.setText("")
    }

    fun setError(editText: EditText, errorString: String) {
        if (editText.parent != null && editText.parent is TextInputLayout) {
            (editText.parent as TextInputLayout).isErrorEnabled = true
            (editText.parent as TextInputLayout).error = errorString
        } else if (editText.parent != null && editText.parent.parent != null && editText.parent.parent is TextInputLayout) {
            (editText.parent.parent as TextInputLayout).isErrorEnabled = true
            (editText.parent.parent as TextInputLayout).error = errorString
        } else {
            editText.error = errorString
        }
    }

    fun setError(editText: EditText?, errorStringId: Int) {
        if (editText != null) {
            setError(editText, editText.resources.getString(errorStringId))
        }
    }

    fun testIsEmpty(editText: EditText?): Boolean {
        return editText == null || TextUtils.isEmpty(getText(editText)) && editText.visibility == View.VISIBLE
    }
}
