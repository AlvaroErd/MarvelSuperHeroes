package com.alerdoci.marvelsuperheroes.app.common.extensions

import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

object Extensions {

    fun String.capitalized(): String {
        return lowercase().replaceFirstChar { it.titlecase() }
    }

    suspend fun <T> runIo(
        function: suspend () -> T
    ): T = withContext(Dispatchers.IO) { function() }

    fun String.toToast(context: Context, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, this, length).show()
    }

    fun Context.getActivity(): AppCompatActivity? = when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

    fun Int.formatVoteCount(): String {
        val df = DecimalFormat("#.#")
        return if (this < 1000) {
            "$this"
        } else {
            val formattedCount = if (this in 1000..999999) {
                df.format((this / 1000.0)).toString().replace(',', '.')
            } else {
                df.format((this / 1000000.0)).toString().replace(',', '.')
            }
            "$formattedCount${if (this in 1000..999999) "k" else "M"}"
        }
    }

}
