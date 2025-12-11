package com.example.recipeandroidapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.model.Ingredient
import com.example.recipeandroidapp.domain.model.Step
import com.example.recipeandroidapp.domain.model.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class RecipeTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromCategoryList(categories: List<Category>): String {
        return gson.toJson(categories)
    }

    @TypeConverter
    fun toCategoryList(data: String): List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromTagList(tags: List<Tag>): String {
        return gson.toJson(tags)
    }

    @TypeConverter
    fun toTagList(data: String): List<Tag> {
        val listType = object : TypeToken<List<Tag>>() {}.type
        return gson.fromJson(data, listType)
    }

    // ---- Ingredient ----
    @TypeConverter
    fun fromIngredientList(ingredients: List<Ingredient>): String = gson.toJson(ingredients)

    @TypeConverter
    fun toIngredientList(data: String): List<Ingredient> {
        val listType = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(data, listType)
    }

    // ---- Step ----
    @TypeConverter
    fun fromStepList(steps: List<Step>): String = gson.toJson(steps)

    @TypeConverter
    fun toStepList(data: String): List<Step> {
        val listType = object : TypeToken<List<Step>>() {}.type
        return gson.fromJson(data, listType)
    }
}