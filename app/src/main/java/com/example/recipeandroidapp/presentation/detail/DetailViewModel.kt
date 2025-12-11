package com.example.recipeandroidapp.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.usecases.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    var state by mutableStateOf(RecipeDetailState())
        private set
    var sideEffect by mutableStateOf<String?>(null)
        private set



    fun onEvent(event: DetailEvent) {
        when(event){
            DetailEvent.BackClick -> {
                /*TODO()*/
            }
            is DetailEvent.UpsertBookmarkRecipe -> {
                viewModelScope.launch {
                    val recipe = recipeUseCases.getRecipeById(event.recipe)
                    if (recipe == null){
                        recipeUseCases.upsertRecipe(event.recipe)
                        sideEffect = "Recipe saved"
                    } else {
                        recipeUseCases.deleteRecipe(recipe)
                        sideEffect = "Recipe deleted"
                    }
                }
            }

            DetailEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    fun loadRecipe(recipe: Recipe) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            try {
                val ingredients = recipeUseCases.getIngredients(recipe.id)
                val steps = recipeUseCases.getSteps(recipe.id)

                state = state.copy(
                    isLoading = false,
                    recipe = recipe,
                    ingredients = ingredients,
                    steps = steps
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

}