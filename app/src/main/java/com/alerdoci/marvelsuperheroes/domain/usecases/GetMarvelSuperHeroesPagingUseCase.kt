package com.alerdoci.marvelsuperheroes.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alerdoci.marvelsuperheroes.data.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.data.features.superheroes.pagination.MarvelSuperHeroesPagingSource
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import javax.inject.Inject

open class GetMarvelSuperHeroesPagingUseCase @Inject constructor(
    private val pager: MarvelSuperHeroesPagingSource
) {

    operator fun invoke(marvelSuperheroes: ModelResult) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 15
        ),
        pagingSourceFactory = {
            pager.apply {
                this.marvelSuperheroes = marvelSuperheroes
            }
        }
    ).flow
}