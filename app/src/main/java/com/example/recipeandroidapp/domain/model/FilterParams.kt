package com.example.recipeandroidapp.domain.model

data class FilterParams(
    val tagIds: Set<Int> = emptySet(),
    val categoryIds: Set<Int> = emptySet(),
    val minTime: Int? = null,
    val maxTime: Int? = null,
    val search: String? = null
)
