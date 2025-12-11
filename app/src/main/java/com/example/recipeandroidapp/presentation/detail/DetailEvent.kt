package com.example.recipeandroidapp.presentation.detail

import com.example.recipeandroidapp.domain.model.Recipe

sealed class DetailEvent {
    data class UpsertBookmarkRecipe(val recipe: Recipe) : DetailEvent()
    object BackClick : DetailEvent()
    object RemoveSideEffect : DetailEvent()
}