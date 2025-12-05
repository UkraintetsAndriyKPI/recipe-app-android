package com.example.recipeandroidapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.recipeandroidapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Find Recipes Easily",
        description = "Search thousands of delicious meals, browse categories and discover new dishes.",
        image = R.drawable.recipes1
    ),
    Page(
        title = "Cook Step by Step",
        description = "Follow simple instructions, save favorites and enjoy cooking like a pro.",
        image = R.drawable.recipes2
    )
)