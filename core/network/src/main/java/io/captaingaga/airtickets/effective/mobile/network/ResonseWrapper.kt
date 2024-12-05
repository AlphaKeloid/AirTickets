package io.captaingaga.airtickets.effective.mobile.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

fun <T> Response<T>.wrapAsFlow(): Flow<T> = flow {
    try {
        if (isSuccessful) {
            body()
                .takeIf { it != null }
                ?.also { emit(it) }
                ?: throw IOException("I/O: Body is null")
        }
    } catch (io: IOException) {
        throw IOException("I/O: ${code()} - ${message()}")
    }
}