package io.salario.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import io.salario.app.data.repo.AuthRepository

class SignInViewModel : ViewModel() {
    private val authRepo = AuthRepository()

    fun signIn(email: String, password: String): Task<AuthResult> {
        return authRepo.signInWithEmailAndPassword(email, password)
    }

    fun resetPassword(email: String): Task<Void> {
        return authRepo.resetPassword(email)
    }
}