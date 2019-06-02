package br.com.walter.walter.core.functional

suspend inline fun <T> resultFrom(crossinline block: suspend () -> T): Result<T> =
    catching { retryIO { block() } }