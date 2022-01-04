package io.salario.app.features.salary_details.presentation.state;

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.salario.app.core.domain.model.UIError
import io.salario.app.features.salary_details.domain.model.Paycheck

data class StatusState(
    val isLoading: Boolean = false,
    val paychecks: List<Paycheck>? = null
) {
    var error by mutableStateOf(UIError())
    var showUploadSuccessDialog by mutableStateOf(false)
    var showExitDialog by mutableStateOf(false)
}
