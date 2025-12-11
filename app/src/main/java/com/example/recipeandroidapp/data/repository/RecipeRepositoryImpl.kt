package com.example.recipeandroidapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.recipeandroidapp.data.remote.RecipeApi
import com.example.recipeandroidapp.data.remote.RecipePagingSource
import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.model.Ingredient
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.model.Step
import com.example.recipeandroidapp.domain.model.Tag
import com.example.recipeandroidapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val recipeApi: RecipeApi
): RecipeRepository {

    override fun getRecipes(
        tagIds: Set<Int>,
        categoryIds: Set<Int>,
        minTime: Int?,
        maxTime: Int?,
        search: String?
    ): Flow<PagingData<Recipe>> {
        return Pager(
            PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                RecipePagingSource(
                    recipeApi = recipeApi,
                    tagIds = tagIds.joinToString(","),
                    categoryIds = categoryIds.joinToString(","),
                    minTime = minTime,
                    maxTime = maxTime,
                    search = search,
                )
            }
        ).flow
    }

    override suspend fun getAllCategories(): Set<Category> {
        return recipeApi.getCategories()
    }

    override suspend fun getAllTags(): Set<Tag> {
        return recipeApi.getTags()
    }

    override suspend fun getIngredients(recipeId: Int): List<Ingredient> =
        recipeApi.getIngredients(recipeId)

    override suspend fun getSteps(recipeId: Int): List<Step> =
        recipeApi.getSteps(recipeId)

    override suspend fun getDailyRecipes(): Flow<List<Recipe>> = flow {
        emit(recipeApi.getDailyRecipes())
    }


}