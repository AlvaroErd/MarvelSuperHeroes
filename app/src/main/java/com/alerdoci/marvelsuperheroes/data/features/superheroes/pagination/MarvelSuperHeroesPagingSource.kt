package com.alerdoci.marvelsuperheroes.data.features.superheroes.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alerdoci.marvelsuperheroes.data.constants.Constants.Companion.PAGE_SIZE
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import javax.inject.Inject

class MarvelSuperHeroesPagingSource @Inject constructor(
    private val repositoryPaging: MarvelRepository,
) : PagingSource<Int, ModelResult>() {
    private val limit = PAGE_SIZE
    var marvelSuperheroes: ModelResult? = null
    override fun getRefreshKey(state: PagingState<Int, ModelResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ModelResult> {
        return try {
            val page = params.key ?: 1
            val response = repositoryPaging.getMarvelSuperHeroes(
                offset = page,
                limit = limit
            ).first()

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpE: HttpException) {
            LoadResult.Error(httpE)
        }
    }

}