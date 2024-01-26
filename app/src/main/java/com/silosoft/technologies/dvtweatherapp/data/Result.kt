package com.silosoft.technologies.dvtweatherapp.data

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
        }
    }
}

val <T> Result<T>.data: T? get() = (this as? Result.Success)?.data

val <T> Result<T>.error: Throwable? get() = (this as? Result.Error)?.error

fun <T> Result<T>.dataOr(fallback: T) = (this as? Result.Success<T>)?.data ?: fallback
