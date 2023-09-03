package com.alerdoci.marvelsuperheroes.datasource.remote.errorhandling

/**
 * [Exception] throw by the remote package when a there is a network connection returns a [Throwable].
 */
class ThrowableWrapperException : Exception {

    constructor() : super()

    constructor(cause: Throwable) : super(cause)
}
