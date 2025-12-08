package com.example.recipeandroidapp.presentation.detail

import com.example.recipeandroidapp.domain.model.Ingredient
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.model.Step

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val steps: List<Step> = emptyList(),
    val ingredients: List<Ingredient> = emptyList()
)