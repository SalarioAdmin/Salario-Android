package io.salario.app.features.salary_details.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.domain.model.InfoDialogConfig
import io.salario.app.core.domain.model.LoadingDialogConfig
import io.salario.app.core.domain.model.UIEvent
import io.salario.app.core.shared_ui.composable.InfoDialogType
import io.salario.app.core.shared_ui.composable.LoadingDialogType
import io.salario.app.core.util.Resource
import io.salario.app.core.util.toDialogType
import io.salario.app.features.salary_details.domain.model.Paycheck
import io.salario.app.features.salary_details.domain.use_case.GetAllUserPaychecks
import io.salario.app.features.salary_details.domain.use_case.UploadPaycheck
import io.salario.app.features.salary_details.presentation.event.StatusEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val uploadPaycheck: UploadPaycheck,
    private val getAllUserPaychecks: GetAllUserPaychecks
) : ViewModel() {
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var showExitDialog by mutableStateOf(false)
    var loadingDialogConfig by mutableStateOf(LoadingDialogConfig())
    var infoDialogConfig by mutableStateOf(InfoDialogConfig())

    val paychecks = MutableSharedFlow<List<Paycheck>>()

    fun onEvent(event: StatusEvent) {
        when (event) {
            is StatusEvent.OnFilePicked -> {
                uploadFile(event.fileBase64)
            }
            is StatusEvent.OnLoadPaychecksRequest -> {
                getUserPaychecks()
            }
            is StatusEvent.OnDialogDismiss -> {
                resetInfoDialog()
            }
            is StatusEvent.OnExitPressed -> {
                showExitDialog = true
            }
            is StatusEvent.OnBackPressed -> {
                showExitDialog = false
            }
        }
    }

    private fun uploadFile(pdfData: String) {
        uploadPaycheck(pdfData)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        resetInfoDialog()
                        configureLoadingDialog(showDialog = true)
                    }
                    is Resource.Error -> {
                        configureLoadingDialog(showDialog = false)
                        configureInfoDialog(
                            message = result.message ?: "",
                            type = result.type!!.toDialogType()
                        )
                    }
                    is Resource.Success -> {
                        configureLoadingDialog(showDialog = false)
                        sendUiEvent(UIEvent.ShowSnackbar("Upload success!"))
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getUserPaychecks() {
        getAllUserPaychecks()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        resetInfoDialog()
                        configureLoadingDialog(showDialog = true)
                    }
                    is Resource.Error -> {
                        configureLoadingDialog(showDialog = false)
                        configureInfoDialog(
                            message = result.message ?: "",
                            type = result.type!!.toDialogType()
                        )
                    }
                    is Resource.Success -> {
                        configureLoadingDialog(showDialog = false)
                        paychecks.emit(result.data as List<Paycheck>)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun configureLoadingDialog(showDialog: Boolean, type: LoadingDialogType? = null) {
        loadingDialogConfig = loadingDialogConfig.copy(
            isActive = showDialog,
            loadingType = type ?: LoadingDialogType.General
        )
    }

    private fun configureInfoDialog(message: String, type: InfoDialogType) {
        infoDialogConfig = InfoDialogConfig(
            title = message,
            infoType = type,
            isActive = true
        )
    }

    private fun resetInfoDialog() {
        if (infoDialogConfig.isActive) {
            infoDialogConfig = InfoDialogConfig()
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}