package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetDailyRecipes(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(): Flow<List<Recipe>> {
        return repository.getDailyRecipes()
    }
}
