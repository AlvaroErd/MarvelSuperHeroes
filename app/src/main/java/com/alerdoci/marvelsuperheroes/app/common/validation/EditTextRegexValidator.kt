package com.alerdoci.marvelsuperheroes.app.common.validation

import android.widget.EditText
import java.util.regex.Pattern

/**
 * Created by bendaniel on 01/04/2016.
 */
class EditTextRegexValidator(editText: EditText, regexPatter: String, errorMessageId: Int) : EditTextValidator(editText, errorMessageId) {

    private val pattern: Pattern = Pattern.compile(regexPatter)

    override val isValid: Boolean
        get() = if (EditTextUtils.testIsEmpty(editText)) {
            false
        } else {
            val matcher = pattern.matcher(EditTextUtils.getText(editText))
            matcher.matches()
        }
}
