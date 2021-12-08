package io.salario.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.salario.app.data.model.UserData
import io.salario.app.data.repo.AuthRepository
import io.salario.app.data.utils.Resource
import io.salario.app.data.utils.Status
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel() {
    private val authRepo = AuthRepository()

    val userData = MutableSharedFlow<Resource<UserData>>()
    val userValidationResult = MutableSharedFlow<Resource<Boolean>>()
    val signUpResult = MutableSharedFlow<Resource<Boolean>>()
    val resetPasswordResult = MutableSharedFlow<Resource<Boolean>>()
    val logoutResult = MutableSharedFlow<Resource<Boolean>>()
    val userAuthState = MutableSharedFlow<Boolean>()

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            signUpResult.emit(Resource(Status.Loading))
            val response = authRepo.createUser(firstName, lastName, email, password)
            val resource = if (response.success) {
                Resource(Status.Success, null, response.message)
            } else {
                Resource(Status.Error, null, response.message)
            }
            signUpResult.emit(resource)
        }
    }

    fun validateNewUser(
        email: String,
        token: String
    ) {
        viewModelScope.launch {
            userValidationResult.emit(Resource(Status.Loading))
            val response = authRepo.validateNewUser(email, token)
            val resource = if (response.success) {
                Resource(Status.Success, null, response.message)
            } else {
                Resource(Status.Error, null, response.message)
            }
            userValidationResult.emit(resource)
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            userData.emit(Resource(Status.Loading))
            val response = authRepo.authenticateUser(email, password)
            val resource = if (response.success) {
                val userData = UserData(response.firstName, response.lastName, response.email)
                authRepo.saveConnectedUser(userData)
                Resource(Status.Success, userData, response.message)
            } else {
                Resource(Status.Error, null, response.message)
            }
            userData.emit(resource)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutResult.emit(Resource(Status.Loading))
            val response = authRepo.logout()
            val resource = if (response.success) {
                authRepo.saveConnectedUser(null)
                Resource(Status.Success)
            } else {
                Resource(Status.Error, null, response.message)
            }
            logoutResult.emit(resource)
        }
    }

    fun resetPassword(
        email: String
    ) {
        viewModelScope.launch {
            resetPasswordResult.emit(Resource(Status.Loading))
            val response = authRepo.resetPassword(email)
            val resource = if (response.success) {
                Resource(Status.Success, null, response.message)
            } else {
                Resource(Status.Error, null, response.message)
            }
            resetPasswordResult.emit(resource)
        }
    }

    fun isUserConnected() {
        viewModelScope.launch {
            userAuthState.emit(authRepo.isUserAuthenticated())
        }
    }

    fun getConnectedUser() = authRepo.getConnectedUser()
}