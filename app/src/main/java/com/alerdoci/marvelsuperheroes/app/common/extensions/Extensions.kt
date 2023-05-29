package com.alerdoci.marvelsuperheroes.app.common.extensions

object Extensions {

    fun String.capitalized(): String {
        return lowercase().replaceFirstChar { it.titlecase() }
    }

}