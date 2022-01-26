package io.salario.app.core.domain.model

import io.salario.app.core.shared_ui.composable.InfoDialogType

data class InfoDialogConfig(
    val title: String = "",
    val subtitle: String = "",
    val infoType: InfoDialogType = InfoDialogType.ErrorGeneral,
    var isActive: Boolean = false
)
