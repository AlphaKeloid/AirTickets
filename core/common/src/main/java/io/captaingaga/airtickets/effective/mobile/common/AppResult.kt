package io.captaingaga.airtickets.effective.mobile.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface AppResult<out T> {
    class Success<T>(val data: T) : AppResult<T>
    data object Loading : AppResult<Nothing>
    class Failure(val exception: Throwable) : AppResult<Nothing>
}

fun <T> Flow<T>.wrapAsAppResult(): Flow<AppResult<T>> =
    map<T, AppResult<T>> { AppResult.Success(it) }
        .onStart { emit(AppResult.Loading) }
        .catch { emit(AppResult.Failure(it)) }