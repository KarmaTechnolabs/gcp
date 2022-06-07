package com.gcptrack.utils

import android.util.Patterns
import com.gcptrack.custom.checkIsNullOrBlank
import java.util.regex.Pattern

object Validator {
    fun isEmailValid(email: CharSequence?): Boolean {
        if (email.checkIsNullOrBlank())
            return false
        return Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
    }

    fun isPasswordValid(password: CharSequence?): Boolean {
        if (password.isNullOrEmpty() || password.isNullOrBlank())
            return false

        //language=RegExp
        val passwordRegex =
            "(?=^.{5,15}\$)(?=.*\\d)(?=.*[!@#\$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*\$"
        return Pattern.compile(passwordRegex).matcher(password).matches()
    }
}