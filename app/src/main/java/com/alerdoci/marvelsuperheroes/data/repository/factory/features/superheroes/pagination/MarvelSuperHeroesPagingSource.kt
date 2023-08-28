package com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superheroes.SuperHeroesResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

open class MarvelSuperHeroesPagingSource @Inject constructor(
    private val repositoryPaging: MarvelRepository,
) : PagingSource<Int, SuperHeroesResult>() {

    private val limit = PAGE_SIZE
    override fun getRefreshKey(state: PagingState<Int, SuperHeroesResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SuperHeroesResult> {
        return try {
            val page = params.key ?: 0
            val response = repositoryPaging.getMarvelSuperHeroesPaging(
                offset = page,
                limit = limit
            ).first()

            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}