package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.data.local.RecipeDao
import com.example.recipeandroidapp.domain.model.Recipe

class UpsertRecipe(
    private val recipeDao: RecipeDao
) {
    suspend operator fun invoke(recipe: Recipe) {
        recipeDao.upsert(recipe = recipe)
    }
}