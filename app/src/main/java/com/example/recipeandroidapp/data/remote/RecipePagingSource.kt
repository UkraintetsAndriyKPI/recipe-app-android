package com.example.recipeandroidapp.data.remote

import androidx.core.net.toUri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipeandroidapp.domain.model.Recipe

class RecipePagingSource(
    private val recipeApi: RecipeApi,
    private val tagIds: String? = null,
    private val categoryIds: String? = null,
    private val minTime: Int? = null,
    private val maxTime: Int? = null,
    private val search: String? = null
) : PagingSource<Int, Recipe>() {
    private var totalRecipeCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val page = params.key ?: 1
        return try {

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

        } catch (e: java.net.SocketTimeoutException) {
            // Таймаут
            e.printStackTrace()
            LoadResult.Error(Exception("Server timeout — please try again"))

        } catch (e: java.net.UnknownHostException) {
            // Немає інтернету
            e.printStackTrace()
            LoadResult.Error(Exception("No internet connection"))

        } catch (e: retrofit2.HttpException) {
            // HTTP помилки 4xx / 5xx
            e.printStackTrace()
            LoadResult.Error(Exception("Server error: ${e.code()}"))

        } catch (e: java.io.IOException) {
            // Інші проблеми з мережею
            e.printStackTrace()
            LoadResult.Error(Exception("Network error"))

        } catch (e: Exception) {
            // Невідомі помилки
            e.printStackTrace()
            LoadResult.Error(Exception("Unexpected error"))
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
}