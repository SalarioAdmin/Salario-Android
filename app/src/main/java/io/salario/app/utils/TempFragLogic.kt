package io.salario.app.utils

//private fun validateCredentials(): Boolean {
//    val isEmailValid = validateEmail()
//    val isPasswordValid = validatePassword()
//
//    return if (isEmailValid && isPasswordValid) {
//        true
//    } else {
//        if (!isEmailValid) {
//            shakeView(binding.emailInputLayout)
//        }
//
//        if (!isPasswordValid) {
//            shakeView(binding.passwordInputLayout)
//        }
//
//        false
//    }
//}
//
//private fun validateEmail(): Boolean {
//    var isValidEmailAddress = false
//    if (binding.emailEt.text.isNullOrBlank()) {
//        binding.emailInputLayout.error = "Email should not be empty"
//    } else if (!isValidEmail(binding.emailEt.text.toString())) {
//        binding.emailInputLayout.error = "Please enter a correct Email address"
//    } else {
//        binding.emailInputLayout.error = null
//        isValidEmailAddress = true
//    }
//
//    return isValidEmailAddress
//}
//
//private fun validatePassword(): Boolean {
//    var isValidPassword = false
//
//    if (binding.passwordEt.text.isNullOrBlank()) {
//        binding.passwordInputLayout.error = "Password should not be empty"
//    } else {
//        when (isValidPassword(binding.passwordEt.text.toString())) {
//            PasswordState.PASSWORD_OK -> {
//                binding.passwordInputLayout.error = null
//                isValidPassword = true
//            }
//
//            PasswordState.ERROR_PASSWORD_TOO_SHORT -> {
//                showError(
//                    "Password should be minimum " +
//                            "$MIN_PASSWORD_LENGTH characters long."
//                )
//            }
//
//            PasswordState.ERROR_PASSWORD_TOO_LONG -> {
//                showError(
//                    "Password should be maximum " +
//                            "$MAX_PASSWORD_LENGTH characters long."
//                )
//            }
//
//            PasswordState.ERROR_MUST_CONTAIN_LETTERS -> {
//                showError("Password should contain letters.")
//            }
//
//            PasswordState.ERROR_MUST_CONTAIN_UPPER_CASE -> {
//                showError("Password should contain at least one capital letter.")
//            }
//
//            PasswordState.ERROR_MUST_CONTAIN_LOWER_CASE -> {
//                showError("Password should contain at least one small letter.")
//            }
//
//            PasswordState.ERROR_MUST_CONTAIN_NUMBER -> {
//                showError("Password should contain at least one number")
//            }
//
//            PasswordState.ERROR_MUST_CONTAIN_SPECIAL_CHARACTER -> {
//                showError("Password should contain at least one special character")
//            }
//        }
//    }
//
//    return isValidPassword
//}
//
//private fun showError(errorText: String) {
//    binding.passwordInputLayout.error = errorText
//}