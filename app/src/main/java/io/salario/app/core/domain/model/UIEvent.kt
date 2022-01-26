package io.salario.app.core.domain.model

sealed class UIEvent {
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ) : UIEvent()

    data class Navigate(val route: String) : UIEvent()
    object PopBackStack : UIEvent()
}
