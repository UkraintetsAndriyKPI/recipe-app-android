package com.example.recipeandroidapp.data.remote

import com.example.recipeandroidapp.data.remote.dto.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipeList")
    suspend fun getRecipes(
        @Query("page") page: Int,
        @Query("tag") tagIds: String? = null,
        @Query("category") categoryIds: String? = null,
        @Query("min_time") minTime: Int? = null,
        @Query("max_time") maxTime: Int? = null,
        @Query("search") search: String? = null
    ): RecipeResponse

}
