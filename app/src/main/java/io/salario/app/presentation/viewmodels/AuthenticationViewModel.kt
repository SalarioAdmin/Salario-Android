package io.salario.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.salario.app.data.model.UserData
import io.salario.app.data.repo.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel() {
    private val authRepo = AuthRepository()

    val userDataFlow = MutableSharedFlow<UserData>()
    val signUpRes = MutableSharedFlow<Boolean>()

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {

        viewModelScope.launch {
            val res = authRepo.createUser(firstName, lastName, email, password)
            signUpRes.emit(res.isSuccess)
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val res = authRepo.authenticateUser(email, password)
            userDataFlow.emit(res)
        }
    }
}