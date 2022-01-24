package io.salario.app.core.util

import io.salario.app.core.util.network.ErrorType

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null, val type: ErrorType? = null) :
        Resource<T>(data, message)
}