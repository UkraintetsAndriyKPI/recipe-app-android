package com.example.recipeandroidapp.presentation.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val searchQuery: String) : SearchEvent()
    data class ToggleTag(val tagId: Int) : SearchEvent()
    data class ToggleCategory(val categoryId: Int) : SearchEvent()
    object ClearFilters : SearchEvent()
    object RefreshSearch : SearchEvent()
    object SearchRecipes : SearchEvent()
}