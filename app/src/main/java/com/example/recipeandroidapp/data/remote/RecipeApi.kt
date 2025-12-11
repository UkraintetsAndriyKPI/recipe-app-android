package com.example.recipeandroidapp.data.remote

import com.example.recipeandroidapp.data.remote.dto.RecipeResponse
import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.model.Ingredient
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.model.Step
import com.example.recipeandroidapp.domain.model.Tag
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipe/filter/")
    suspend fun getRecipes(
        @Query("page") page: Int,
        @Query("tag") tagIds: String? = null,
        @Query("category") categoryIds: String? = null,
        @Query("min_time") minTime: Int? = null,
        @Query("max_time") maxTime: Int? = null,
        @Query("search") search: String? = null
    ): RecipeResponse

    @GET("recipe/{id}/ingredients/")
    suspend fun getIngredients(
        @Path("id") recipeId: Int
    ): List<Ingredient>

    @GET("recipe/{id}/steps/")
    suspend fun getSteps(
        @Path("id") recipeId: Int
    ): List<Step>

    @GET("categories/")
    suspend fun getCategories(): Set<Category>

    @GET("tags/")
    suspend fun getTags(): Set<Tag>

    @GET("recipe/daily-recipes/")
    suspend fun getDailyRecipes(): List<Recipe>
}
