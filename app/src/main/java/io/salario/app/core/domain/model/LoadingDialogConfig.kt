package io.salario.app.core.domain.model

import io.salario.app.core.shared_ui.composable.LoadingDialogType

data class LoadingDialogConfig(
    val loadingType: LoadingDialogType = LoadingDialogType.General,
    var isActive: Boolean = false
)
