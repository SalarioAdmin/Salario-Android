package io.salario.app.core.util.network

import io.salario.app.core.util.Resource
import retrofit2.HttpException
import java.io.IOException

fun IOException.getError(): Resource.Error<Any> {
    return Resource.Error(
        message = message ?: "",
        type = ErrorType.IO
    )
}

fun HttpException.getError(): Resource.Error<Any> {
    return when (code()) {
        400, 422 -> {
            Resource.Error(
                message = message(),
                type = ErrorType.WrongInput
            )
        }

        401 -> {
            Resource.Error(
                message = message(),
                type = ErrorType.Unauthorized
            )
        }

        403 -> {
            Resource.Error(
                message = message(),
                type = ErrorType.Forbidden
            )
        }

        404 -> {
            Resource.Error(
                message(),
                type = ErrorType.NotFound
            )
        }

        500 -> {
            Resource.Error(
                message(),
                type = ErrorType.ServerError
            )
        }

        else -> {
            Resource.Error(
                message = message(),
                type = ErrorType.ServerError
            )
        }
    }
}


sealed class ErrorType {
    object IO : ErrorType()
    object WrongInput : ErrorType()
    object NotFound : ErrorType()
    object Unauthorized : ErrorType()
    object Forbidden : ErrorType()
    object ServerError : ErrorType()
}