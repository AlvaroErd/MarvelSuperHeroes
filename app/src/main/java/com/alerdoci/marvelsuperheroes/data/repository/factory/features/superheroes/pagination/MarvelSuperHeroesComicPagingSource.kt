package com.alerdoci.marvelsuperheroes.data.repository.factory.features.superheroes.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

open class MarvelSuperHeroesComicPagingSource @Inject constructor(
    private val repositoryPaging: MarvelRepository,
    private val superHeroId: Int

) : PagingSource<Int, ModelComicsResult>() {
    private val limit = PAGE_SIZE
    override fun getRefreshKey(state: PagingState<Int, ModelComicsResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ModelComicsResult> =
        try {
            val page = params.key ?: 0
            val response = repositoryPaging.getMarvelSuperHeroComics(
                offset = page,
                limit = limit,
                superHeroId = superHeroId,
            ).first()

            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
}
