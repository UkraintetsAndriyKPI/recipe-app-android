package com.example.recipeandroidapp.domain.model

data class Recipe(
    val title: String,
    val description: String,
    val cooking_time_min: Int,
    val creation_date: String,
    val is_published: Boolean,
    val image: String?,
    val categories: List<Category>,
    val tags: List<Tag>
)