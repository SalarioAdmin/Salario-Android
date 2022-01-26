package io.salario.app.features.auth.presentation.viewmodel

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
import io.salario.app.core.shared_ui.state_holder.TextFieldState
import io.salario.app.core.util.*
import io.salario.app.features.auth.domain.use_case.CreateUser
import io.salario.app.features.auth.presentation.event.SignUpEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUser: CreateUser
) : ViewModel() {
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var showWelcomeDialog by mutableStateOf(false)
    var loadingDialogConfig by mutableStateOf(
        LoadingDialogConfig()
    )
    var infoDialogConfig by mutableStateOf(InfoDialogConfig())
    val emailInputState = TextFieldState(validate = { it.validateEmail() })
    val passwordInputState = TextFieldState(validate = { it.validatePassword() })
    val firstNameInputState: TextFieldState = TextFieldState(validate = { it.validateFirstName() })
    val lastNameInputState: TextFieldState = TextFieldState(validate = { it.validateLastName() })

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnSignUpPressed -> {
                signUp(event.firstName, event.lastName, event.email, event.password)
            }
            is SignUpEvent.OnSignInPressed -> {
                sendUiEvent(UIEvent.Navigate(Destination.SignInDestination.route))
            }
            is SignUpEvent.OnDialogDismiss -> {
                resetInfoDialog()
            }
        }
    }

    private fun signUp(firstName: String, lastName: String, email: String, password: String) {
        createUser(firstName, lastName, email, password)
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
                        showWelcomeDialog = true
                        delay(2000)
                        sendUiEvent(UIEvent.Navigate(FEATURES_GRAPH_ROUTE))
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