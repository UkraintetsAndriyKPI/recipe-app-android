package com.example.recipeandroidapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Recipe(
    val id: Int,
    @PrimaryKey
    val title: String,
    val description: String,
    val cooking_time_min: Int,
    val creation_date: String,
    val is_published: Boolean,
    val image: String?,
    val categories: List<Category>,
    val tags: List<Tag>
): Parcelable