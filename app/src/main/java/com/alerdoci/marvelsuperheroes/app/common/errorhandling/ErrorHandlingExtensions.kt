package com.alerdoci.marvelsuperheroes.app.common.errorhandling

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

inline fun <T> Flow<T>.onError(crossinline errorAction: suspend (error: Throwable) -> Unit): Flow<T> =
    catch { e -> errorAction(e) }
