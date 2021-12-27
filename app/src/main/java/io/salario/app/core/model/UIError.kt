package io.salario.app.core.model

import io.salario.app.core.shared_ui.composable.DialogInfoType

data class UIError(
    val text: String = "",
    val dialogType: DialogInfoType = DialogInfoType.ErrorGeneral,
    var isActive: Boolean = false
)
