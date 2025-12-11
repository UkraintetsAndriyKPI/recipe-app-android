package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.repository.RecipeRepository

class GetAllCategories(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(): Set<Category> {
        return recipeRepository.getAllCategories()
    }
}