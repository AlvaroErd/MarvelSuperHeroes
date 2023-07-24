package com.alerdoci.marvelsuperheroes.app.common.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Extensions {

    fun String.capitalized(): String {
        return lowercase().replaceFirstChar { it.titlecase() }
    }

    suspend fun <T> runIo(
        function: suspend () -> T
    ): T = withContext(Dispatchers.IO) { function() }

}