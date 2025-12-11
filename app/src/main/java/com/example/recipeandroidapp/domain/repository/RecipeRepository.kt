package com.example.recipeandroidapp.domain.repository

import androidx.paging.PagingData
import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.model.Ingredient
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.model.Step
import com.example.recipeandroidapp.domain.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface RecipeRepository {
    fun getRecipes(
        tagIds: Set<Int> = emptySet(),
        categoryIds: Set<Int> = emptySet(),
        minTime: Int? = null,
        maxTime: Int? = null,
        search: String? = null
    ): Flow<PagingData<Recipe>>

    suspend fun getAllCategories(): Set<Category>

    suspend fun getAllTags(): Set<Tag>

    suspend fun getIngredients(recipeId: Int): List<Ingredient>
    suspend fun getSteps(recipeId: Int): List<Step>

    suspend fun getDailyRecipes(): Flow<List<Recipe>>
}