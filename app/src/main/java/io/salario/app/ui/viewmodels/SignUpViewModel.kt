package io.salario.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import io.salario.app.data.repo.AuthRepository

class SignUpViewModel : ViewModel() {
    private val authRepo = AuthRepository()

    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Task<AuthResult> {
        return authRepo.registerUserWithEmailAndPassword(firstName, lastName, email, password)
    }

    fun sendVerificationEmail(): Task<Void>? {
        return authRepo.sendVerificationEmail()
    }
}