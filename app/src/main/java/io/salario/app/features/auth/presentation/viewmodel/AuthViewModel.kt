package io.salario.app.features.auth.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.use_case.*
import io.salario.app.features.auth.presentation.state.EmailValidationState
import io.salario.app.features.auth.presentation.state.SignInState
import io.salario.app.features.auth.presentation.state.SignUpState
import io.salario.app.features.auth.presentation.state.UserAuthState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val createUser: CreateUser,
    private val validateUserCreation: ValidateUserCreation,
    private val authenticateUser: AuthenticateUser,
    private val resetPassword: ResetPasswordRequest,
    private val logout: Logout,
    private val getConnectedUser: GetConnectedUser
) : ViewModel() {
    private val _signUpState = mutableStateOf(SignUpState())
    val signUpState: State<SignUpState> = _signUpState

    private val _signInState = mutableStateOf(SignInState())
    val signInState: State<SignInState> = _signInState

    private val _emailValidationState = mutableStateOf(EmailValidationState())
    val emailValidationState: State<EmailValidationState> = _emailValidationState

    private val _userAuthState = mutableStateOf(UserAuthState())
    val userAuthState: State<UserAuthState> = _userAuthState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onSignUp(firstName: String, lastName: String, email: String, password: String) {
        createUser(firstName, lastName, email, password)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _signUpState.value = signUpState.value.copy(
                            errorMessage = "Error",
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Error"))
                    }
                    is Resource.Loading -> {
                        _signUpState.value = signUpState.value.copy(
                            errorMessage = "",
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _signUpState.value = signUpState.value.copy(
                            errorMessage = "",
                            isLoading = false,
                            shouldNavigateToValidation = true
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Success!"))
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onValidateUserCreation(email: String, userCreationValidationToken: String) {
        validateUserCreation(email, userCreationValidationToken)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _emailValidationState.value = emailValidationState.value.copy(
                            errorMessage = "Error",
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Error"))
                    }
                    is Resource.Loading -> {
                        _emailValidationState.value = emailValidationState.value.copy(
                            errorMessage = "",
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _emailValidationState.value = emailValidationState.value.copy(
                            errorMessage = "",
                            isLoading = false,
                            shouldNavigateToStatus = true
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Success!"))
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onSignIn(email: String, password: String) {
        authenticateUser(email, password)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _signInState.value = signInState.value.copy(
                            errorMessage = "Error",
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Error"))
                    }
                    is Resource.Loading -> {
                        _signInState.value = signInState.value.copy(
                            errorMessage = "",
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _signInState.value = signInState.value.copy(
                            errorMessage = "",
                            isLoading = false,
                            signInSuccess = true
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Success!"))
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onResetPassword(email: String) {
        resetPassword(email)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _signInState.value = signInState.value.copy(
                            errorMessage = "Error",
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Error"))
                    }
                    is Resource.Loading -> {
                        _signInState.value = signInState.value.copy(
                            errorMessage = "",
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _signInState.value = signInState.value.copy(
                            errorMessage = "",
                            isLoading = false,
                            resetPasswordSuccess = true
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Success!"))
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onLogout() {
        logout()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _userAuthState.value = userAuthState.value.copy(
                            isLoading = false,
                            shouldLogout = true
                        )
                    }
                    is Resource.Loading -> {
                        _userAuthState.value = userAuthState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _userAuthState.value = userAuthState.value.copy(
                            errorMessage = "Error",
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar("Error"))
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getLoggedInUser() {
        getConnectedUser()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _userAuthState.value = userAuthState.value.copy(
                            isLoading = false,
                            errorMessage = "",
                            isConnected = true,
                            userData = result.data
                        )
                    }
                    is Resource.Loading -> {
                        _userAuthState.value = userAuthState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _userAuthState.value = userAuthState.value.copy(
                            errorMessage = "Error",
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}