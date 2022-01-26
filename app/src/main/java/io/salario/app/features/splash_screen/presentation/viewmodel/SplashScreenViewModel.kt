package io.salario.app.features.splash_screen.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.salario.app.core.domain.model.UIEvent
import io.salario.app.core.domain.use_case.GetConnectedUser
import io.salario.app.core.navigation.Destination
import io.salario.app.core.navigation.FEATURES_GRAPH_ROUTE
import io.salario.app.core.util.Resource
import io.salario.app.features.splash_screen.presentation.event.SplashScreenEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val getConnectedUser: GetConnectedUser
) : ViewModel() {
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SplashScreenEvent) {
        when (event) {
            SplashScreenEvent.OnAnimationFinished -> {
                getLoggedInUser()
            }
        }
    }

    private fun getLoggedInUser() {
        getConnectedUser()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        sendUiEvent(UIEvent.Navigate(FEATURES_GRAPH_ROUTE))
                    }
                    is Resource.Error -> {
                        sendUiEvent(UIEvent.Navigate(Destination.IntroDestination.route))
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}