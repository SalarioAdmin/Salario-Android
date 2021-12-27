package io.salario.app.core.util

import android.util.Patterns
import java.util.regex.Pattern

const val MIN_PASSWORD_LENGTH = 8
const val MAX_PASSWORD_LENGTH = 20
const val SPECIAL_CHARACTERS = "~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?"
val PASSWORD_PATTERN: Pattern =
    Pattern.compile(
        "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$SPECIAL_CHARACTERS])" +
                ".{$MIN_PASSWORD_LENGTH,$MAX_PASSWORD_LENGTH}\$"
    )
val UPPER_CASE_PATTERN: Pattern = Pattern.compile(".*[A-Z].*")
val LOWER_CASE_PATTERN: Pattern = Pattern.compile(".*[a-z].*")
val NUMBERS_PATTERN: Pattern = Pattern.compile(".*[0-9].*")
val SPECIAL_CHARACTERS_PATTERN: Pattern = Pattern.compile(".*[$SPECIAL_CHARACTERS].*")

fun isValidEmail(emailInput: String) = Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()

fun isValidPassword(passwordInput: String): PasswordValidationResult {
    val validationResult: PasswordValidationResult

    when {
        passwordInput.length < MIN_PASSWORD_LENGTH ->
            validationResult = PasswordValidationResult.ErrorPasswordTooShort

        passwordInput.length > MAX_PASSWORD_LENGTH ->
            validationResult = PasswordValidationResult.ErrorPasswordTooLong

        !UPPER_CASE_PATTERN.matcher(passwordInput).matches() &&
                !LOWER_CASE_PATTERN.matcher(passwordInput).matches() ->
            validationResult = PasswordValidationResult.ErrorPasswordShouldContainLetters

        !UPPER_CASE_PATTERN.matcher(passwordInput).matches() ->
            validationResult = PasswordValidationResult.ErrorPasswordShouldContainCapitalLetter

        !LOWER_CASE_PATTERN.matcher(passwordInput).matches() ->
            validationResult = PasswordValidationResult.ErrorPasswordShouldContainLowerCaseLetter

        !NUMBERS_PATTERN.matcher(passwordInput).matches() ->
            validationResult = PasswordValidationResult.ErrorPasswordShouldContainNumber

        !SPECIAL_CHARACTERS_PATTERN.matcher(passwordInput).matches() ->
            validationResult = PasswordValidationResult.ErrorPasswordShouldContainSpecialCharacter

        else -> {
            validationResult = PasswordValidationResult.PasswordOk
        }
    }

    return validationResult
}

fun getPasswordValidationError(password: String): String? {
    return when (isValidPassword(password)) {
        is PasswordValidationResult.PasswordOk -> {
            null
        }

        is PasswordValidationResult.ErrorPasswordTooShort -> {
            "Password should be minimum " +
                    "$MIN_PASSWORD_LENGTH characters long."
        }

        is PasswordValidationResult.ErrorPasswordTooLong -> {
            "Password should be maximum " +
                    "$MAX_PASSWORD_LENGTH characters long."
        }

        is PasswordValidationResult.ErrorPasswordShouldContainLetters -> {
            "Password should contain letters."
        }

        is PasswordValidationResult.ErrorPasswordShouldContainCapitalLetter -> {
            "Password should contain at least one capital letter."
        }

        is PasswordValidationResult.ErrorPasswordShouldContainLowerCaseLetter -> {
            "Password should contain at least one small letter."
        }

        is PasswordValidationResult.ErrorPasswordShouldContainNumber -> {
            "Password should contain at least one number"
        }

        is PasswordValidationResult.ErrorPasswordShouldContainSpecialCharacter -> {
            "Password should contain at least one special character"
        }
    }
}

sealed class PasswordValidationResult {
    object PasswordOk : PasswordValidationResult()
    object ErrorPasswordTooShort : PasswordValidationResult()
    object ErrorPasswordTooLong : PasswordValidationResult()
    object ErrorPasswordShouldContainLetters : PasswordValidationResult()
    object ErrorPasswordShouldContainCapitalLetter : PasswordValidationResult()
    object ErrorPasswordShouldContainLowerCaseLetter : PasswordValidationResult()
    object ErrorPasswordShouldContainNumber : PasswordValidationResult()
    object ErrorPasswordShouldContainSpecialCharacter : PasswordValidationResult()
}