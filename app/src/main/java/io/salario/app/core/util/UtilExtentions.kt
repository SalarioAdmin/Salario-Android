package io.salario.app.core.util

import io.salario.app.core.shared_ui.composable.InfoDialogType
import io.salario.app.core.util.network.ErrorType


fun ErrorType.toDialogType(): InfoDialogType {
    return when (this) {
        ErrorType.IO -> InfoDialogType.ErrorNoConnection
        ErrorType.ServerError -> InfoDialogType.ErrorGeneral
        ErrorType.WrongInput -> InfoDialogType.ErrorWrongCredentials
        else -> InfoDialogType.ErrorGeneral
    }
}