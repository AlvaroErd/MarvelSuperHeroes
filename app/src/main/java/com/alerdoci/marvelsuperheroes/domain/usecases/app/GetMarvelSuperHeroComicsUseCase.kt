package com.alerdoci.marvelsuperheroes.domain.usecases.app

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.pagination.MarvelSuperHeroesComicPagingSource
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import javax.inject.Inject

class GetMarvelSuperHeroComicsUseCase @Inject constructor(
    private val repository: MarvelRepository
) {
    operator fun invoke(superHeroId: Int) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 40,
        ),
        pagingSourceFactory = {
            MarvelSuperHeroesComicPagingSource(repository, superHeroId)
        }
    ).flow
}
