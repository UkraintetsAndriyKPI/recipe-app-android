package com.example.recipeandroidapp.presentation.detail

sealed class DetailEvent {
    object BookmarkRecipe : DetailEvent()
    object BackClick : DetailEvent()
}