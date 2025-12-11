package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.domain.model.Tag
import com.example.recipeandroidapp.domain.repository.RecipeRepository

class GetAllTags(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(): Set<Tag> {
        return recipeRepository.getAllTags()
    }
}