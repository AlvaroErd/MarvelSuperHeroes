package com.alerdoci.marvelsuperheroes.app.common.validation

import android.widget.EditText
import java.util.regex.Pattern

/**
 * Created by Daniel on 9/11/2015.
 */
class EditTextUsernameValidator(editText: EditText, errorMessageId: Int) : EditTextValidator(editText, errorMessageId) {

    private val pattern: Pattern

    init {
        pattern = Pattern.compile(USERNAME_PATTERN)
    }

    override val isValid: Boolean
        get() = if (EditTextUtils.testIsEmpty(editText)) {
            false
        } else {
            val matcher = pattern.matcher(EditTextUtils.getText(editText))
            matcher.matches()
        }

    companion object {

        private const val USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$"
    }
}
