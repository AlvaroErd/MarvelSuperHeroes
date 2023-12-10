package com.alerdoci.marvelsuperheroes.datasource.remote.errorhandling

import android.util.Log
import com.alerdoci.marvelsuperheroes.model.exception.HTTPException
import io.reactivex.rxjava3.annotations.NonNull
import retrofit2.HttpException
import java.io.IOException

/**
 * This class converts any error thrown by the data layer, [Throwable] or [Exception], into an exception.
 *
 * The idea is to digest the errors and make them friendly to the presentation layer. Hence, there are exceptions that
 * will be returned "as is", and other exceptions like [HttpException] from Retrofit that must be converted to an
 * app-defined exception so that Retrofit is not imported in app layer classes to deal with them.
 */
object RemoteExceptionMapper {

    fun getException(@NonNull throwable: Throwable): Exception {

        return when (throwable) {
            // Map Retrofit HTTP exception
            is HttpException -> {
                val statusCode = throwable.code()
                val responseBody = throwable.response()?.errorBody()
                responseBody?.let {
                    try {
                        HTTPException(responseBody.string(), throwable, statusCode)
                    } catch (e: IOException) {
                        Log.e(e.toString(), "Unable to extract Retrofit HTTP exception body")
                        IllegalArgumentException("Retrofit HTTP exception cannot be parsed")
                    }
                } ?: IllegalArgumentException("Retrofit HTTP exception cannot be parsed")
            }
            // Return other exceptions as-is
            is Exception -> throwable
            // Wrap any throwable but non-exception classes with a exception
            else -> ThrowableWrapperException(throwable)
        }
    }
}
