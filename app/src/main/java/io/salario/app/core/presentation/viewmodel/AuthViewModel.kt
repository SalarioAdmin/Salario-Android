package io.salario.app.core.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.use_case.GetConnectedUser
import io.salario.app.features.auth.domain.use_case.Logout
import io.salario.app.features.auth.presentation.state.UserAuthState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val logout: Logout,
    private val getConnectedUser: GetConnectedUser
) : ViewModel() {

    var userAuthState by mutableStateOf(UserAuthState())
        private set

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onLogout() {
        logout()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        userAuthState = userAuthState.copy(
                            isLoading = false,
                            shouldLogout = true
                        )
                    }
                    is Resource.Loading -> {
                        userAuthState = userAuthState.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        userAuthState = userAuthState.copy(
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
                        userAuthState = userAuthState.copy(
                            isLoading = false,
                            errorMessage = "",
                            isConnected = true,
                            userData = result.data
                        )
                    }
                    is Resource.Loading -> {
                        userAuthState = userAuthState.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        userAuthState = userAuthState.copy(
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