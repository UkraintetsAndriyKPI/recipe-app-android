package com.example.recipeandroidapp.domain.repository

import androidx.paging.PagingData
import com.example.recipeandroidapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(
        tagIds: List<Int>? = null,
        categoryIds: List<Int>? = null,
        minTime: Int? = null,
        maxTime: Int? = null,
        search: String? = null
    ): Flow<PagingData<Recipe>>
}