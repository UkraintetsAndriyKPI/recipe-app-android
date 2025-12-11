package com.example.recipeandroidapp.presentation.search

import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.model.Tag


data class SearchState(
    val searchQuery: String = "",
    val selectedTags: Set<Int> = emptySet(),
    val selectedCategories: Set<Int> = emptySet(),

    val allTags: Set<Tag> = emptySet(),
    val allCategories: Set<Category> = emptySet(),

    val isLoading: Boolean = false,
    val error: String? = null
)