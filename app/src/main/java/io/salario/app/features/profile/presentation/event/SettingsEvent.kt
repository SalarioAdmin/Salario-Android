package io.salario.app.features.profile.presentation.event

sealed class SettingsEvent {
    object OnLogoutPressed : SettingsEvent()
    object OnBackPressed : SettingsEvent()
    object OnDialogDismiss : SettingsEvent()
}
