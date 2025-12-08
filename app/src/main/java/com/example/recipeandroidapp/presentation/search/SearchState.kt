package com.example.recipeandroidapp.presentation.search

import androidx.paging.PagingData
import com.example.recipeandroidapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow


data class SearchState(
    val searchQuery: String = "",
    val selectedTags: Set<Int> = emptySet(),
    val selectedCategories: Set<Int> = emptySet(),
    val recipes: Flow<PagingData<Recipe>>? = null
) {
}