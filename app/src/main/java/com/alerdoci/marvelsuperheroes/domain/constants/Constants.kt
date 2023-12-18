package com.alerdoci.marvelsuperheroes.domain.constants

sealed class Constants {
    companion object {
        const val OFFSET = 0
        const val PAGE_SIZE = 20
        const val IMAGE_NOT_FOUND = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"
        const val DOT = "."
        const val HTTP = "http"
        const val HTTPS = "https"
    }
}
