package com.alerdoci.marvelsuperheroes.domain.usecases.app

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.pagination.MarvelSuperHeroesPagingSource
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import javax.inject.Inject

open class GetMarvelSuperHeroesPagingUseCase @Inject constructor(
    private val repository: MarvelRepository
) {
    operator fun invoke(query: String? = null) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true,
            prefetchDistance = PAGE_SIZE / 2,
            initialLoadSize = PAGE_SIZE / 2,
        ),
        pagingSourceFactory = { MarvelSuperHeroesPagingSource(repository, query) }
    ).flow
}
