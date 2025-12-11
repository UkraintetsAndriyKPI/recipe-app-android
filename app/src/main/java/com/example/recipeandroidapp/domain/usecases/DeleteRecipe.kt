package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.data.local.RecipeDao
import com.example.recipeandroidapp.domain.model.Recipe

class DeleteRecipe(
    private val recipeDao: RecipeDao
) {
    suspend operator fun invoke(recipe: Recipe) {
        recipeDao.delete(recipe = recipe)
    }
}