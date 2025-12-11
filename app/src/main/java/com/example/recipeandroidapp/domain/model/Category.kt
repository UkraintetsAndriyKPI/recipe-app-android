package com.example.recipeandroidapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val category_name: String,
    val id: Int
): Parcelable