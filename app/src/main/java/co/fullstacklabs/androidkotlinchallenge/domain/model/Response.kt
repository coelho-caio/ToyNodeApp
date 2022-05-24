package co.fullstacklabs.androidkotlinchallenge.domain.model

sealed class Response<out T : Any> {
    data class Success<out T : Any>(val data:T): Response<T>()
    data class Failure(val exception: Exception): Response<Nothing>()
}