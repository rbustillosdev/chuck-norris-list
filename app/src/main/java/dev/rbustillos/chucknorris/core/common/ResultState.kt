package dev.rbustillos.chucknorris.core.common

sealed class ResultState <out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultState<T>()
    data class Error(val exception: Exception) : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
}
