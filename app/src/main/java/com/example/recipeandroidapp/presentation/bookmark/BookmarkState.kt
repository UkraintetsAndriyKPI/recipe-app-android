package com.example.recipeandroidapp.presentation.bookmark

import com.example.recipeandroidapp.domain.model.Recipe

data class BookmarkState(
    val recipes: List<Recipe> = emptyList()
)