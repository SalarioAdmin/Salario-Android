package io.salario.app.features.settings.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.use_case.Logout
import io.salario.app.features.auth.presentation.state.UserAuthState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logout: Logout
) : ViewModel() {

    var authState by mutableStateOf(UserAuthState())
        private set

    fun onLogout() {
        logout()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        authState = authState.copy(
                            isLoading = false,
                            shouldLogout = true
                        )
                    }
                    is Resource.Loading -> {
                        authState = authState.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        authState = authState.copy(
                            errorMessage = "Error",
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}