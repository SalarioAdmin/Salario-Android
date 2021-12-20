package io.salario.app.features.splash_screen.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.data.local.cache.Cache
import io.salario.app.core.util.Resource
import io.salario.app.features.auth.domain.use_case.GetConnectedUser
import io.salario.app.features.auth.presentation.state.UserAuthState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val getConnectedUser: GetConnectedUser
) : ViewModel() {

    var authState by mutableStateOf(UserAuthState())
        private set

    fun getLoggedInUser() {
        getConnectedUser()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        authState = authState.copy(
                            isLoading = false,
                            errorMessage = "",
                            isConnected = true,
                            userData = result.data
                        )
                        // TODO check if is a correct way
                        Cache.user = result.data
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