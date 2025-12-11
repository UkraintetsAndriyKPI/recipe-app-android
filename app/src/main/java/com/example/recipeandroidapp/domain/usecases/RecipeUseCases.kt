package com.example.recipeandroidapp.domain.usecases

data class RecipeUseCases(
    // Recipe API
    val getRecipes: GetRecipes,
    val getDailyRecipes: GetDailyRecipes,
    val searchRecipes: SearchRecipes,

    // Room
    val upsertRecipe: UpsertRecipe,
    val deleteRecipe: DeleteRecipe,
    val selectRecipes: SelectRecipes,
    val getRecipeById: GetRecipeById,

    // Categories + Tags API
    val getAllCategories: GetAllCategories,
    val getAllTags: GetAllTags,


    // Ingredients & Steps
    val getIngredients: GetIngredients,
    val getSteps: GetSteps,
)