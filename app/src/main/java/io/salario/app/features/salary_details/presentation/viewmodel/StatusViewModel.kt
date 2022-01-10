package io.salario.app.features.salary_details.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.domain.model.UIError
import io.salario.app.core.shared_ui.composable.DialogInfoType
import io.salario.app.core.util.ErrorType
import io.salario.app.core.util.Resource
import io.salario.app.features.salary_details.domain.use_case.GetAllUserPaychecks
import io.salario.app.features.salary_details.domain.use_case.UploadPaycheck
import io.salario.app.features.salary_details.presentation.state.StatusState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val uploadPaycheck: UploadPaycheck,
    private val getAllUserPaychecks: GetAllUserPaychecks
) : ViewModel() {
    // TODO rename it
    var statusState by mutableStateOf(StatusState())
        private set

    fun onUploadPaycheck(pdfData: String) {
        uploadPaycheck(pdfData)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        statusState = statusState.copy(
                            isLoading = false
                        ).apply {
                            error = UIError(
                                result.message!!,
                                dialogType = when (result.type) {
                                    ErrorType.IO -> DialogInfoType.ErrorNoConnection
                                    ErrorType.ServerError -> DialogInfoType.ErrorGeneral
                                    ErrorType.WrongInput -> DialogInfoType.ErrorWrongCredentials
                                    else -> DialogInfoType.ErrorGeneral
                                },
                                isActive = true
                            )
                        }
                    }
                    is Resource.Loading -> {
                        statusState = statusState.copy(
                            isLoading = true
                        ).apply {
                            statusState.error = statusState.error.copy(
                                isActive = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        statusState = statusState.copy(
                            isLoading = false
                        ).apply {
                            statusState.error = statusState.error.copy(
                                isActive = false
                            )
                            showUploadSuccessDialog = true
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun getUserPaychecks() {
        getAllUserPaychecks()
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        statusState = statusState.copy(
                            isLoading = false
                        ).apply {
                            error = UIError(
                                result.message!!,
                                dialogType = when (result.type) {
                                    ErrorType.IO -> DialogInfoType.ErrorNoConnection
                                    ErrorType.ServerError -> DialogInfoType.ErrorGeneral
                                    ErrorType.WrongInput -> DialogInfoType.ErrorWrongCredentials
                                    else -> DialogInfoType.ErrorGeneral
                                },
                                isActive = true
                            )
                        }
                    }
                    is Resource.Loading -> {
                        statusState = statusState.copy(
                            isLoading = true
                        ).apply {
                            statusState.error = statusState.error.copy(
                                isActive = false
                            )
                        }
                    }
                    is Resource.Success -> {
                        statusState = statusState.copy(
                            isLoading = false,
                            paychecks = result.data
                        ).apply {
                            statusState.error = statusState.error.copy(
                                isActive = false
                            )
                            showUploadSuccessDialog = true
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun setErrorDialogVisibility(visible: Boolean) {
        statusState.error = statusState.error.copy(
            isActive = visible
        )
    }

    fun setExitDialogVisibility(visible: Boolean) {
        statusState.showExitDialog = visible
    }

    fun setUploadSuccessDialogVisibility(visible: Boolean) {
        statusState.showUploadSuccessDialog = visible
    }
}