package com.example.recipeandroidapp.domain.repository

import androidx.paging.PagingData
import com.example.recipeandroidapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(
        tagIds: Set<Int> = emptySet(),
        categoryIds: Set<Int> = emptySet(),
        minTime: Int? = null,
        maxTime: Int? = null,
        search: String? = null
    ): Flow<PagingData<Recipe>>
}