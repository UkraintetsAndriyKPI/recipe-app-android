package com.example.recipeandroidapp.presentation.home

import com.example.recipeandroidapp.domain.model.Recipe

data class DailyRecipesState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val error: String? = null
)