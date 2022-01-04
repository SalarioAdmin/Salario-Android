package io.salario.app.core.util

import com.auth0.android.jwt.JWT
import io.salario.app.core.domain.model.User

fun JWT.getUser(): User? {
    val firstName = getClaim("firstName").asString()
    val lastName = getClaim("lastName").asString()
    val email = getClaim("email").asString()

    return if (firstName != null && lastName != null && email != null) {
        User(firstName, lastName, email)
    } else {
        null
    }
}