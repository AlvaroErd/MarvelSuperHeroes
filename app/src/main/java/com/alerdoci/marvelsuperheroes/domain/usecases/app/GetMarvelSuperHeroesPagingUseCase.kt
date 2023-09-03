package com.alerdoci.marvelsuperheroes.domain.usecases.app

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.pagination.MarvelSuperHeroesPagingSource
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import javax.inject.Inject

open class GetMarvelSuperHeroesPagingUseCase @Inject constructor(
    private val superHeroPaging: MarvelSuperHeroesPagingSource
) {

    open operator fun invoke() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 40,
        ),
        pagingSourceFactory = { superHeroPaging }
    ).flow
}
