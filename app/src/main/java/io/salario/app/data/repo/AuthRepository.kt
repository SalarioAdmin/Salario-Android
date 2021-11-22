package io.salario.app.data.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import io.salario.app.data.source.remote.firebase.FirebaseAuthManager

class AuthRepository {
    private val firebaseAuth = FirebaseAuthManager

    fun registerUserWithEmailAndPassword(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Task<AuthResult> {
        return firebaseAuth.registerUser(firstName, lastName, email, password)
    }

    fun sendVerificationEmail(): Task<Void>? {
        return firebaseAuth.sendVerificationEmail()
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Task<AuthResult> {
        return firebaseAuth.signIn(email, password)
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun resetPassword(email: String): Task<Void> {
        return firebaseAuth.resetPassword(email)
    }
}