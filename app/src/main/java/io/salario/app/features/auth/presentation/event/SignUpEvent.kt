package io.salario.app.features.auth.presentation.event

sealed class SignUpEvent {
    data class OnSignUpPressed(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String
    ) : SignUpEvent()

    object OnSignInPressed : SignUpEvent()
    object OnDialogDismiss : SignUpEvent()
}
