package com.alerdoci.marvelsuperheroes.app.common.states

import com.alerdoci.marvelsuperheroes.app.common.states.error.ErrorBundle

sealed class ResourceState<out T> {

    class Idle<T> : ResourceState<T>()
    class Loading<T> : ResourceState<T>()
    data class Success<T>(val data: T) : ResourceState<T>()
    data class Error<T>(val errorBundle: ErrorBundle) : ResourceState<T>()
}
