package io.salario.app.features.auth.presentation.event

sealed class SignInEvent {
    data class OnSignInPressed(val email: String, val password: String): SignInEvent()
    data class OnForgotPasswordPressed(val email: String): SignInEvent()
    object OnSignUpPressed: SignInEvent()
    object OnDialogDismiss: SignInEvent()
}
