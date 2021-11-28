package io.salario.app.data.source.remote.firebase

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthManager {
    private val auth = FirebaseAuth.getInstance()

    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun sendVerificationEmail(): Task<Void>? {
        return auth.currentUser?.sendEmailVerification()
    }

    fun signIn(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun resetPassword(email: String): Task<Void> {
        return auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("yoss", "password reset successfully")
                } else {
                    Log.i("yoss", "fuck", it.exception)
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }
}