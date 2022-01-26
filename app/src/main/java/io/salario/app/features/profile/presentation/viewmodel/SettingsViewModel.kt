package io.salario.app.features.profile.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.domain.model.InfoDialogConfig
import io.salario.app.core.domain.model.LoadingDialogConfig
import io.salario.app.core.domain.model.UIEvent
import io.salario.app.core.navigation.Destination
import io.salario.app.core.navigation.FEATURES_GRAPH_ROUTE
import io.salario.app.core.shared_ui.composable.InfoDialogType
import io.salario.app.core.shared_ui.composable.LoadingDialogType
import io.salario.app.core.util.Resource
import io.salario.app.core.util.toDialogType
import io.salario.app.features.auth.domain.use_case.Logout
import io.salario.app.features.profile.presentation.event.SettingsEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logout: Logout
) : ViewModel() {
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var loadingDialogConfig by mutableStateOf(
        LoadingDialogConfig()
    )
    var infoDialogConfig by mutableStateOf(InfoDialogConfig())

    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.OnLogoutPressed -> {
                logoutUser()
            }
            SettingsEvent.OnBackPressed -> {
                sendUiEvent(UIEvent.Navigate(FEATURES_GRAPH_ROUTE))
            }
            SettingsEvent.OnDialogDismiss -> {
                resetInfoDialog()
            }
        }
    }

    private fun logoutUser() {
        logout()
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
                        sendUiEvent(UIEvent.Navigate(Destination.IntroDestination.route))
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