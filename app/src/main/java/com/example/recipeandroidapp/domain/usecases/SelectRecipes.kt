package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.data.local.RecipeDao
import com.example.recipeandroidapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

class SelectRecipes(
    private val recipeDao: RecipeDao
) {
    operator fun invoke(): Flow<List<Recipe>> {
        return recipeDao.getRecipes()
    }
}