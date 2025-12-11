package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.domain.model.Step
import com.example.recipeandroidapp.domain.model.Tag
import com.example.recipeandroidapp.domain.repository.RecipeRepository

class GetSteps(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: Int): List<Step> =
        repository.getSteps(recipeId)
}
