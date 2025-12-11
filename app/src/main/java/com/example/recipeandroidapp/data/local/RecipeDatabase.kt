package com.example.recipeandroidapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeandroidapp.domain.model.Recipe

@Database(entities = [Recipe::class], version = 2)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase: RoomDatabase() {
    abstract val recipeDao: RecipeDao
}
