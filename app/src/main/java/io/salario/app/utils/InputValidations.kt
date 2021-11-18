package io.salario.app.utils

import android.util.Patterns
import java.util.regex.Pattern

const val MIN_PASSWORD_LENGTH = 8
const val MAX_PASSWORD_LENGTH = 20
const val SPECIAL_CHARACTERS = "~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?"
val PASSWORD_PATTERN: Pattern =
    Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$SPECIAL_CHARACTERS])" +
            ".{$MIN_PASSWORD_LENGTH,$MAX_PASSWORD_LENGTH}\$")
val UPPER_CASE_PATTERN: Pattern = Pattern.compile(".*[A-Z].*")
val LOWER_CASE_PATTERN: Pattern = Pattern.compile(".*[a-z].*")
val NUMBERS_PATTERN: Pattern = Pattern.compile(".*[0-9].*")
val SPECIAL_CHARACTERS_PATTERN: Pattern = Pattern.compile(".*[$SPECIAL_CHARACTERS].*")

fun isValidEmail(emailInput: String) = Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()

fun isValidPassword(passwordInput: String): PasswordState {
    var state = PasswordState.PASSWORD_OK

    when {
        passwordInput.length < MIN_PASSWORD_LENGTH ->
            state = PasswordState.ERROR_PASSWORD_TOO_SHORT

        passwordInput.length > MAX_PASSWORD_LENGTH ->
            state = PasswordState.ERROR_PASSWORD_TOO_LONG

        !UPPER_CASE_PATTERN.matcher(passwordInput).matches() &&
            !LOWER_CASE_PATTERN.matcher(passwordInput).matches() ->
            state = PasswordState.ERROR_MUST_CONTAIN_LETTERS

        !UPPER_CASE_PATTERN.matcher(passwordInput).matches() ->
            state = PasswordState.ERROR_MUST_CONTAIN_UPPER_CASE

        !LOWER_CASE_PATTERN.matcher(passwordInput).matches() ->
            state = PasswordState.ERROR_MUST_CONTAIN_LOWER_CASE

        !NUMBERS_PATTERN.matcher(passwordInput).matches() ->
            state = PasswordState.ERROR_MUST_CONTAIN_NUMBER

        !SPECIAL_CHARACTERS_PATTERN.matcher(passwordInput).matches() ->
            state = PasswordState.ERROR_MUST_CONTAIN_SPECIAL_CHARACTER
    }

    return state
}

enum class PasswordState {
    ERROR_PASSWORD_TOO_SHORT,
    ERROR_PASSWORD_TOO_LONG,
    ERROR_MUST_CONTAIN_LETTERS,
    ERROR_MUST_CONTAIN_UPPER_CASE,
    ERROR_MUST_CONTAIN_LOWER_CASE,
    ERROR_MUST_CONTAIN_NUMBER,
    ERROR_MUST_CONTAIN_SPECIAL_CHARACTER,
    PASSWORD_OK
}