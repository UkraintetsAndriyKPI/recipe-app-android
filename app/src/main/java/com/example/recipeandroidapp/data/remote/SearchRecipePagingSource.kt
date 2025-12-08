package com.example.recipeandroidapp.data.remote

import androidx.core.net.toUri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipeandroidapp.domain.model.Recipe

class SearchRecipePagingSource(
    private val recipeApi: RecipeApi,
    private val tagIds: String? = null,
    private val categoryIds: String? = null,
    private val minTime: Int? = null,
    private val maxTime: Int? = null,
    private val search: String? = null
) : PagingSource<Int, Recipe>() {

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPosition = state.anchorPosition
            if (anchorPosition != null) {
                val anchorPage = state.closestPageToPosition(anchorPosition)
                if (anchorPage != null) {
                    if (anchorPage.prevKey != null) {
                        return anchorPage.prevKey!! + 1
                    } else if (anchorPage.nextKey != null) {
                        return anchorPage.nextKey!! - 1
                    }
                }
            }
            return null
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val page = params.key ?: 1

            val response = recipeApi.getRecipes(
                page = page,
                tagIds = tagIds,
                categoryIds = categoryIds,
                minTime = minTime,
                maxTime = maxTime,
                search = search
            )

            val recipes = response.results

            LoadResult.Page(
                data = recipes,
                prevKey = getPageNumberFromUrl(response.previous),
                nextKey = getPageNumberFromUrl(response.next)
            )

        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    private fun getPageNumberFromUrl(url: String?): Int? {
        if (url == null) return null
        return try {
            val uri = url.toUri()
            uri.getQueryParameter("page")?.toInt()
        } catch (e: Exception) {
            null
        }
    }
}
