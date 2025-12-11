package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.domain.model.Ingredient
import com.example.recipeandroidapp.domain.repository.RecipeRepository

class GetIngredients(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: Int): List<Ingredient> =
        repository.getIngredients(recipeId)
}
