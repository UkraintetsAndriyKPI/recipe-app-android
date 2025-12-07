package com.example.recipeandroidapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.recipeandroidapp.data.remote.RecipeApi
import com.example.recipeandroidapp.data.remote.RecipePagingSource
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(
    private val recipeApi: RecipeApi
): RecipeRepository {

    override fun getRecipes(
        tagIds: List<Int>?,
        categoryIds: List<Int>?,
        minTime: Int?,
        maxTime: Int?,
        search: String?
    ): Flow<PagingData<Recipe>> {
        return Pager(
            PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                RecipePagingSource(
                    recipeApi = recipeApi,
                    tagIds = tagIds?.joinToString(","),
                    categoryIds = categoryIds?.joinToString(","),
                    minTime = minTime,
                    maxTime = maxTime,
                    search = search,
                )
            }
        ).flow
    }
}