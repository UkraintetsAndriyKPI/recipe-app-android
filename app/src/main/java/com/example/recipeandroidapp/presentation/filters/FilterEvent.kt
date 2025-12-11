package com.example.recipeandroidapp.presentation.filters

sealed class FilterEvent {
    data class ToggleCategory(val categoryId: Int) : FilterEvent()
    data class ToggleTag(val tagId: Int) : FilterEvent()
    object ApplyFilters : FilterEvent()
}