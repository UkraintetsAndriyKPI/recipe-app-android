package com.example.recipeandroidapp.domain.usecases

import androidx.paging.PagingData
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetRecipes(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(
        tagIds: List<Int>? = null,
        categoryIds: List<Int>? = null,
        minTime: Int? = null,
        maxTime: Int? = null,
        search: String? = null
    ): Flow<PagingData<Recipe>> {
        return recipeRepository.getRecipes(
            tagIds = tagIds,
            categoryIds = categoryIds,
            minTime = minTime,
            maxTime = maxTime,
            search = search
        )
    }
}
