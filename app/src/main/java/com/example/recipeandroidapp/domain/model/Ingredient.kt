package com.example.recipeandroidapp.domain.model

data class Ingredient(
    val ingredient_name: String,
    val quantity: String?,
    val unit: String,
    val position: Int
)