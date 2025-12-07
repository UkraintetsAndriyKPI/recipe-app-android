package com.example.recipeandroidapp.data.remote.dto

import com.example.recipeandroidapp.domain.model.Recipe

data class RecipeResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Recipe>
)