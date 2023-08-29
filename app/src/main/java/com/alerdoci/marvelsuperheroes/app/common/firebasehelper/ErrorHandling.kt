package com.alerdoci.marvelsuperheroes.app.common.firebasehelper

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

fun getErrorMessage(e: Exception): String {
    return "Not Implemented yet"
}

fun getLoginErrorMessage(e: Exception): String {
    return when (e) {
        is FirebaseAuthInvalidUserException -> "User not found"
        is FirebaseAuthInvalidCredentialsException -> "Wrong email or password"
        is WrongAccountTypeException -> "Wrong account type"
        else -> "Unknown Error"
    }
}

fun getCreateAccountErrorMessage(e: Exception): String {
    return when (e) {
        is FirebaseAuthUserCollisionException -> "The email address is already in use by another account"
        else -> "Unknown Error"
    }
}

class WrongAccountTypeException: Exception()

