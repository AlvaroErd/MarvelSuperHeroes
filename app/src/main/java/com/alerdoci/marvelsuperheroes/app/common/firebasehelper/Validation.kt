package com.alerdoci.marvelsuperheroes.app.common.firebasehelper

import android.util.Patterns

fun validateEmail(email: String): ValidationResult {
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return ValidationResult(
            isValid = false,
            message = "Wrong email format"
        )
    }
    return ValidationResult(
        isValid = true,
        message = null
    )
}

fun validateFirstName(name: String): ValidationResult {
    return if (name.isEmpty()) {
        ValidationResult(
            isValid = false,
            message = "First name cannot be empty"
        )
    } else {
        ValidationResult(
            isValid = true,
            message = null
        )
    }
}

fun validateLastName(name: String): ValidationResult {
    return if (name.isEmpty()) {
        ValidationResult(
            isValid = false,
            message = "Last name cannot be empty"
        )
    } else {
        ValidationResult(
            isValid = true,
            message = null
        )
    }
}

fun validatePassword(password: String): ValidationResult {
    if (password.length < 6) {
        return ValidationResult(
            isValid = false,
            message = "Password must be more than 6 characters"
        )
    }
    return ValidationResult(
        isValid = true,
        message = null
    )
}

fun validateImages(images: List<ByteArray>): ValidationResult {
    if (images.isEmpty()) {
        return ValidationResult(
            isValid = false,
            message = "Select one image at least"
        )
    }
    return ValidationResult(
        isValid = true,
        message = null
    )
}

fun validateDescription(description: String): ValidationResult {
    return if (description.isEmpty()) {
        ValidationResult(
            isValid = false,
            message = "Add description"
        )
    } else {
        ValidationResult(
            isValid = true,
            message = null
        )
    }
}

fun validatePrice(price: Double): ValidationResult {
    return if (price == 0.0) {
        ValidationResult(
            isValid = false,
            message = "Add price"
        )
    } else {
        ValidationResult(
            isValid = true,
            message = null
        )
    }
}

fun validateCurrency(currency: String): ValidationResult {
    return if (currency.isBlank()) {
        ValidationResult(
            isValid = false,
            message = "Add currency"
        )
    } else {
        ValidationResult(
            isValid = true,
            message = null
        )
    }
}

data class ValidationResult(
    val isValid: Boolean,
    val message: String?,
)
