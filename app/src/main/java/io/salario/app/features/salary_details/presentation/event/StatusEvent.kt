package io.salario.app.features.salary_details.presentation.event

sealed class StatusEvent {
    object OnDialogDismiss : StatusEvent()
    object OnExitPressed : StatusEvent()
    object OnLoadPaychecksRequest : StatusEvent()
    object OnBackPressed : StatusEvent()
    data class OnFilePicked(val fileBase64: String) : StatusEvent()
}
