package com.alerdoci.marvelsuperheroes.model.exception

class HTTPException(
    override val message: String,
    override val cause: Throwable,
    val statusCode: Int
) : RuntimeException("HTTP $statusCode $message", cause)