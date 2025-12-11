package com.example.recipeandroidapp.presentation.filters

import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.model.Tag

data class FilterState(
    val selectedCategories: Set<Int> = emptySet(),
    val selectedTags: Set<Int> = emptySet(),
    val allCategories: Set<Category> = emptySet(),
    val allTags: Set<Tag> = emptySet()
)